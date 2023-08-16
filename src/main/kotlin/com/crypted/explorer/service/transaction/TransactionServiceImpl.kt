package com.crypted.explorer.service.transaction

import com.crypted.explorer.api.model.domain.transaction.transfer.Erc1155TransferMongoDO
import com.crypted.explorer.api.model.domain.transaction.transfer.Erc20TransferMongoDO
import com.crypted.explorer.api.model.domain.transaction.transfer.Erc721TransferMongoDO
import com.crypted.explorer.api.model.domain.transaction.transfer.TokenTransferMongoDO
import com.crypted.explorer.api.model.domain.transaction.CheckpointMongoDO
import com.crypted.explorer.api.model.domain.transaction.InflationMongoDO
import com.crypted.explorer.api.model.domain.transaction.TransactionMongoDO
import com.crypted.explorer.api.service.token.TokenService
import com.crypted.explorer.api.service.transaction.TransactionService
import com.crypted.explorer.common.constant.TokenCode
import com.crypted.explorer.common.constant.TokenType
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.repository.MongoUtils
import com.crypted.explorer.common.util.MathUtils
import com.crypted.explorer.common.util.Text
import com.crypted.explorer.gateway.model.resp.transaction.TokenTransferListResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionInfoResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionListResp
import com.crypted.explorer.api.model.vo.transaction.TokenTransferListVO
import com.crypted.explorer.gateway.model.resp.transaction.TokenTransferredVO
import com.crypted.explorer.api.model.vo.token.TokenVO
import com.crypted.explorer.api.model.vo.transaction.TransactionHistoryVO
import com.crypted.explorer.api.model.vo.transaction.TransactionListVO
import com.crypted.explorer.api.service.account.AccountService
import com.crypted.explorer.common.constant.TransactionCode
import com.crypted.explorer.service.account.AccountRepository
import com.crypted.explorer.service.transaction.transfer.Erc1155TransferMongoRepository
import com.crypted.explorer.service.transaction.transfer.Erc20TransferMongoRepository
import com.crypted.explorer.service.transaction.transfer.Erc721TransferMongoRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties

@Service
class TransactionServiceImpl(
    private val mongoUtils: MongoUtils,
    private val transactionMongoRepository: TransactionMongoRepository,
    private val inflationMongoRepository: InflationMongoRepository,
    private val erc20TransferMongoRepository: Erc20TransferMongoRepository,
    private val erc721TransferMongoRepository: Erc721TransferMongoRepository,
    private val erc1155TransferMongoRepository: Erc1155TransferMongoRepository,
    private val accountRepository: AccountRepository,
    private val checkpointMongoRepository: CheckpointMongoRepository,
    private val methodRepository: MethodRepository,
    private val accountService: AccountService,
    private val tokenService: TokenService
) : TransactionService {

    companion object {
        private val LOG = LoggerFactory.getLogger(TransactionServiceImpl::class.java)
    }

    private val COLLECTION_NAME_TRANSACTIONS = "transactions"

    private val FIELD_NAME_FROM = "from"

    private val FIELD_NAME_TO = "to"

    private val FIELD_NAME_BLOCKNUMBER = "blockNumber"

    private val FIELD_NAME_STATUS = "status"

    private val FIELD_NAME_TRANSACTIONINDEX = "transactionIndex"

    private val FIELD_NAME_CREATED_AT = "createdAt"

    private val FIELD_NAME_LOGINDEX = "logIndex"

    private val COLLECTION_NAME_ERC20_TRANSFERS = "erc20_transfers"

    private val COLLECTION_NAME_ERC721_TRANSFERS = "erc1721_transfers"

    private val COLLECTION_NAME_ERC1155_TRANSFERS = "erc1155_transfers"

    private val FIELD_NAME_TOKEN_ADDRESS = "tokenAddress"

    @Value("\${transaction.value.symbol}")
    private lateinit var symbol: String

    @Value("\${transaction.history.size}")
    private lateinit var historySize: String

    override fun getListByPage(
        fromAddress: String?,
        toAddress: String?,
        blockNumber: Long?,
        status: Int?,
        pageNumber: Int,
        pageSize: Int
    ): Result<TransactionListResp?> {

        val pageable: Pageable = PageRequest.of(
            pageNumber - 1, pageSize, Sort.by(
                Sort.Order(Sort.Direction.DESC, FIELD_NAME_BLOCKNUMBER),
                Sort.Order(Sort.Direction.ASC, FIELD_NAME_TRANSACTIONINDEX)
            )
        )

        val transactionMongoDOList: List<TransactionMongoDO> =
            this.findByFromOrToOrBlockNumberAndStatus(fromAddress, toAddress, blockNumber, status, pageable)
        val totalTx: Int = this.countByFromOrToOrBlockNumberAndStatus(fromAddress, toAddress, blockNumber, status)

        val addressIsContractMap = getAccountIsContractMap(transactionMongoDOList)
        val methodMap = getMethodMap()

        val transactionList: List<TransactionListVO?> = transactionMongoDOList.stream().map { transactionMongoDO ->
            val transactionListVO = TransactionListVO()
            transactionListVO.status = transactionMongoDO?.status
            transactionListVO.txHash = transactionMongoDO!!.hash
            transactionListVO.method =
                methodMap[transactionMongoDO.functionSignature] ?: transactionMongoDO.functionName
                        ?: transactionMongoDO.functionSignature
            transactionListVO.blockNumber = transactionMongoDO.blockNumber
            transactionListVO.blockTimestamp = transactionMongoDO.blockTimestamp
            transactionListVO.from = transactionMongoDO.from?.let { accountService.getByAddress(it).data }
            transactionListVO.to = transactionMongoDO.to?.let { accountService.getByAddress(it).data }
            transactionListVO.value = transactionMongoDO.value
            transactionListVO.txFee = transactionMongoDO.fee
            transactionListVO.symbol = this@TransactionServiceImpl.symbol
            transactionListVO
        }.toList()

        val transactionListResp = TransactionListResp().apply {
            this.totalPage = MathUtils.ceilDiv(totalTx, pageSize)
            this.totalTx = totalTx
            this.transactionList = transactionList
        }

        return Result.success(transactionListResp)
    }

    override fun getInfoByTxHash(txHash: String): Result<TransactionInfoResp?> {

        val transactionMongoDO: TransactionMongoDO = transactionMongoRepository.findByHash(txHash)

        val transactionInfoResp = TransactionInfoResp().apply {
            this.txHash = transactionMongoDO.hash
            this.blockNumber = transactionMongoDO.blockNumber
            this.blockTimestamp = transactionMongoDO.blockTimestamp
            this.from = transactionMongoDO.from?.let { accountService.getByAddress(it).data }
            this.to = transactionMongoDO.to?.let { accountService.getByAddress(it).data }
            this.value = transactionMongoDO.value
            this.txFee = transactionMongoDO.fee
            this.symbol = this@TransactionServiceImpl.symbol
            this.status = transactionMongoDO.status
        }

        return Result.success(transactionInfoResp)

    }

    override fun getTokenTransferredByTxHash(txHash: String): Result<TokenTransferredVO?> {

        val tokenTransferMongoDO: TokenTransferMongoDO? = erc20TransferMongoRepository.findByTransactionHash(txHash)
            ?: erc721TransferMongoRepository.findByTransactionHash(txHash)
            ?: erc1155TransferMongoRepository.findByTransactionHash(txHash)
        tokenTransferMongoDO?.let {
            val tokenVO: TokenVO? = tokenTransferMongoDO.tokenAddress?.let { tokenService.getByAddress(it) }?.data
            val tokenTransferVO = TokenTransferredVO().apply {
                this.txHash = tokenTransferMongoDO.transactionHash
                this.from = tokenTransferMongoDO.from?.let { accountService.getByAddress(it).data }
                this.to = tokenTransferMongoDO.to?.let { accountService.getByAddress(it).data }
                this.value = tokenTransferMongoDO.value
                this.name = tokenVO?.name
                this.symbol = tokenVO?.symbol
            }
            return Result.success(tokenTransferVO)
        } ?: run {
            return Result.failure(TransactionCode.NOT_TOKEN_TRANSFER.code, TransactionCode.NOT_TOKEN_TRANSFER.message)
        }
    }

    override fun getTransactionAmountByBlockNumber(blockNumber: Long): Result<Int> {

        return Result.success(transactionMongoRepository.countByBlockNumber(blockNumber))
    }

    override fun getHistory(): Result<List<TransactionHistoryVO>?> {

        val pageable: Pageable = PageRequest.of(0, historySize.toInt(), Sort.Direction.DESC, FIELD_NAME_CREATED_AT)

        val checkpointMongoDOList: List<CheckpointMongoDO> = checkpointMongoRepository.findAll(pageable).content

        val inflationMongoDOList: List<InflationMongoDO> =
            inflationMongoRepository.findByCheckpointIn(checkpointMongoDOList.map { it.id!! })

        val transactionList: List<TransactionHistoryVO> = checkpointMongoDOList.map { checkpointMongoDO ->
            val inflationMongoDO = inflationMongoDOList.find { it.checkpoint == checkpointMongoDO.id }
            val transactionHistoryVO = TransactionHistoryVO().apply {
                this.date = checkpointMongoDO.createdAt?.let { MathUtils.convertTimeZone(it) }
                this.count = inflationMongoDO?.transactionCount
            }
            transactionHistoryVO
        }.toList()

        return Result.success(transactionList)
    }

    private fun findByFromOrToOrBlockNumberAndStatus(
        fromAddress: String?,
        toAddress: String?,
        blockNumber: Long?,
        status: Int?,
        pageable: Pageable
    ): List<TransactionMongoDO> {
        val andCriteriaList = mutableListOf<Criteria>()
        val orCriteriaList = mutableListOf<Criteria>()

        if (!fromAddress.isNullOrBlank()) {
            orCriteriaList.add(Criteria.where(FIELD_NAME_FROM).`is`(fromAddress))
        }

        if (!toAddress.isNullOrBlank()) {
            orCriteriaList.add(Criteria.where(FIELD_NAME_TO).`is`(toAddress))
        }

        if (blockNumber != null && blockNumber != 0L) {
            orCriteriaList.add(Criteria.where(FIELD_NAME_BLOCKNUMBER).`is`(blockNumber))
        }

        if (status != null) {
            andCriteriaList.add(Criteria.where(FIELD_NAME_STATUS).`is`(status))
        }

        return mongoUtils.getByPage(pageable, TransactionMongoDO::class, andCriteriaList, orCriteriaList)
    }

    private fun countByFromOrToOrBlockNumberAndStatus(
        fromAddress: String?,
        toAddress: String?,
        blockNumber: Long?,
        status: Int?
    ): Int {
        val andCriteriaList = mutableListOf<Criteria>()
        val orCriteriaList = mutableListOf<Criteria>()

        if (!fromAddress.isNullOrBlank()) {
            orCriteriaList.add(Criteria.where(FIELD_NAME_FROM).`is`(fromAddress))
        }

        if (!toAddress.isNullOrBlank()) {
            orCriteriaList.add(Criteria.where(FIELD_NAME_TO).`is`(toAddress))
        }

        if (blockNumber != null && blockNumber != 0L) {
            orCriteriaList.add(Criteria.where(FIELD_NAME_BLOCKNUMBER).`is`(blockNumber))
        }

        if (status != null) {
            andCriteriaList.add(Criteria.where(FIELD_NAME_STATUS).`is`(status))
        }

        return mongoUtils.getCountWithNativeQuery(COLLECTION_NAME_TRANSACTIONS, andCriteriaList, orCriteriaList)
    }

    private inline fun <reified T : Any> getAccountIsContractMap(entityList: List<T>): Map<String, Boolean> {
        val fromGetter = T::class.declaredMemberProperties.find { it.name == FIELD_NAME_FROM }
        val toGetter = T::class.declaredMemberProperties.find { it.name == FIELD_NAME_TO }

        val addressList = entityList.flatMap { listOf(fromGetter?.get(it), toGetter?.get(it)) }
            .filterIsInstance<String>()
            .distinct()
        val addressDOList = accountRepository.findByAddressIn(addressList)

        return addressDOList.associate { it.address!! to (it.isContract == 1) }
    }

    private fun getMethodMap(): Map<String, String> {
        val methodDOList = methodRepository.findAll()
        return methodDOList.mapNotNull { it.signature?.let { sig -> it.label?.let { label -> sig to label } } }
            .filter { Text.isNotBlank(it.second) }
            .toMap()
    }

    override fun getTransferListByTokenAddress(
        tokenAddress: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<TokenTransferListResp?> {

        val result4TokenVO = tokenService.getByAddress(tokenAddress)
        if (result4TokenVO.isSuccess()) {
            val pageable: Pageable = PageRequest.of(
                pageNumber - 1, pageSize, Sort.by(
                    Sort.Order(Sort.Direction.DESC, FIELD_NAME_BLOCKNUMBER),
                    Sort.Order(Sort.Direction.ASC, FIELD_NAME_LOGINDEX)
                )
            )

            val tokenTransferMongoDOList: List<TokenTransferMongoDO> =
                when (result4TokenVO.data?.type?.let { Text.cleanAndLowercase(it) }) {
                    TokenType.ERC20.value -> this.getTransfersByTokenAddress(
                        tokenAddress,
                        pageable,
                        Erc20TransferMongoDO::class
                    )

                    TokenType.ERC721.value -> this.getTransfersByTokenAddress(
                        tokenAddress,
                        pageable,
                        Erc721TransferMongoDO::class
                    )

                    TokenType.ERC1155.value -> this.getTransfersByTokenAddress(
                        tokenAddress,
                        pageable,
                        Erc1155TransferMongoDO::class
                    )

                    else -> return Result.failure(TokenCode.UNKNOWN_TOKEN.code, TokenCode.UNKNOWN_TOKEN.message)
                }
            val totalTx: Int = when (result4TokenVO.data.type?.let { Text.cleanAndLowercase(it) }) {
                TokenType.ERC20.value -> this.countTransfersByTokenAddress(tokenAddress, COLLECTION_NAME_ERC20_TRANSFERS)
                TokenType.ERC721.value -> this.countTransfersByTokenAddress(tokenAddress, COLLECTION_NAME_ERC721_TRANSFERS)
                TokenType.ERC1155.value -> this.countTransfersByTokenAddress(tokenAddress, COLLECTION_NAME_ERC1155_TRANSFERS)
                else -> return Result.failure(TokenCode.UNKNOWN_TOKEN.code, TokenCode.UNKNOWN_TOKEN.message)
            }

            val addressIsContractMap = getAccountIsContractMap(tokenTransferMongoDOList)
            val methodMap = getMethodMap()

            val tokenTransferList: List<TokenTransferListVO?> =
                tokenTransferMongoDOList.stream().map { tokenTransferMongoDO ->
                    val tokenTransferListVO = TokenTransferListVO()
                    tokenTransferListVO.txHash = tokenTransferMongoDO.transactionHash
                    tokenTransferListVO.method = tokenTransferMongoDO.functionName
                    tokenTransferListVO.method =
                        methodMap[tokenTransferMongoDO.functionSignature] ?: tokenTransferMongoDO.functionName
                                ?: tokenTransferMongoDO.functionSignature
                    tokenTransferListVO.blockNumber = tokenTransferMongoDO.blockNumber
                    tokenTransferListVO.blockTimestamp = tokenTransferMongoDO.blockTimestamp
                    tokenTransferListVO.from = tokenTransferMongoDO.from?.let { accountService.getByAddress(it) }?.data
                    tokenTransferListVO.to = tokenTransferMongoDO.to?.let { accountService.getByAddress(it) }?.data
                    tokenTransferListVO.value = tokenTransferMongoDO.value
                    tokenTransferListVO.symbol = result4TokenVO.data.symbol
                    tokenTransferListVO
                }.toList()

            return Result.success(TokenTransferListResp().apply {
                this.totalPage = MathUtils.ceilDiv(totalTx, pageSize)
                this.totalTx = totalTx
                this.tokenTransferList = tokenTransferList
            })
        } else {
            return Result.failure(TokenCode.UNKNOWN_TOKEN.code, TokenCode.UNKNOWN_TOKEN.message)
        }
    }

    private fun <T : Any> getTransfersByTokenAddress(
        tokenAddress: String,
        pageable: Pageable,
        entityType: KClass<T>
    ): List<T> {
        val criteriaList = mutableListOf<Criteria>()
        criteriaList.add(Criteria.where(FIELD_NAME_TOKEN_ADDRESS).`is`(tokenAddress))
        return mongoUtils.getByPage(pageable, entityType, criteriaList)
    }

    private fun countTransfersByTokenAddress(tokenAddress: String, collectionName: String): Int {
        val criteriaList = mutableListOf<Criteria>()
        criteriaList.add(Criteria.where(FIELD_NAME_TOKEN_ADDRESS).`is`(tokenAddress))
        return mongoUtils.getCountWithNativeQuery(collectionName, criteriaList)
    }

    override fun getTotalTransfers(tokenType: String, tokenAddress: String): Result<Int> {

        return when (Text.cleanAndLowercase(tokenType)) {
            TokenType.ERC20.value -> Result.success(erc20TransferMongoRepository.countByTokenAddress(tokenAddress))
            TokenType.ERC721.value -> Result.success(erc721TransferMongoRepository.countByTokenAddress(tokenAddress))
            TokenType.ERC1155.value -> Result.success(erc1155TransferMongoRepository.countByTokenAddress(tokenAddress))
            else -> Result.success(0)
        }
    }
}
