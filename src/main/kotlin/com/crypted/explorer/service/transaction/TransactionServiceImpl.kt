package com.crypted.explorer.service.transaction

import com.crypted.explorer.api.model.domain.transaction.InflationMongoDO
import com.crypted.explorer.api.model.domain.transaction.TransactionMongoDO
import com.crypted.explorer.api.service.account.AccountService
import com.crypted.explorer.api.service.token.TokenService
import com.crypted.explorer.api.service.transaction.TransactionService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.repository.MongoUtils
import com.crypted.explorer.common.util.MathUtils
import com.crypted.explorer.gateway.model.resp.transaction.TransactionInfoResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionListResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionTokenInfoResp
import com.crypted.explorer.gateway.model.vo.token.TokenTransferVO
import com.crypted.explorer.gateway.model.vo.transaction.TransactionHistoryVO
import com.crypted.explorer.gateway.model.vo.transaction.TransactionListVO
import com.crypted.explorer.service.account.AccountRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
    private val mongoUtils: MongoUtils,
    private val transactionMongoRepository: TransactionMongoRepository,
    private val inflationMongoRepository: InflationMongoRepository,
    private val tokenService: TokenService,
    private val accountRepository: AccountRepository,
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

        val pageable: Pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(
            Sort.Order(Sort.Direction.DESC, FIELD_NAME_BLOCKNUMBER),
            Sort.Order(Sort.Direction.ASC, FIELD_NAME_TRANSACTIONINDEX)
        ))

        val transactionMongoDOList: List<TransactionMongoDO?> =
            this.findByFromOrToOrBlockNumberAndStatus(fromAddress, toAddress, blockNumber, status, pageable)
        val totalTx: Int = this.countByFromOrToOrBlockNumberAndStatus(fromAddress, toAddress, blockNumber, status)

        val addressIsContractMap = getAccountIsContractMap(transactionMongoDOList)
        val transactionList: List<TransactionListVO?> = transactionMongoDOList.stream().map { transactionMongoDO ->
            val transactionListVO = TransactionListVO()
            transactionListVO.status = transactionMongoDO?.status
            transactionListVO.txHash = transactionMongoDO!!.hash
            transactionListVO.method = transactionMongoDO.functionName ?: transactionMongoDO.functionSignature
            transactionListVO.blockNumber = transactionMongoDO.blockNumber
            transactionListVO.blockTimestamp = transactionMongoDO.blockTimestamp
            transactionListVO.from = transactionMongoDO.from
            transactionListVO.fromIsContract = addressIsContractMap.get(transactionMongoDO.from)
            transactionListVO.to = transactionMongoDO.to
            transactionListVO.toIsContract = addressIsContractMap.get(transactionMongoDO.to)
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
            this.timestamp = transactionMongoDO.createdAt?.time?.div(1000)
            this.from = transactionMongoDO.from
            this.to = transactionMongoDO.to
            this.value = transactionMongoDO.value
            this.txFee = transactionMongoDO.fee
            this.symbol = this@TransactionServiceImpl.symbol
            this.status = transactionMongoDO.status
        }

        return Result.success(transactionInfoResp)

    }

    override fun getTokenInfoByTxHash(txHash: String): Result<TransactionTokenInfoResp?> {

        val tokenTransferVO: TokenTransferVO? = tokenService.getTokenInfoByTxHash(txHash).data

        val transactionTokenInfoResp = TransactionTokenInfoResp()
        tokenTransferVO?.let { BeanUtils.copyProperties(it, transactionTokenInfoResp) }

        return Result.success(transactionTokenInfoResp)

    }

    override fun getTransactionAmountByBlockNumber(blockNumber: Long): Result<Int> {

        return Result.success(transactionMongoRepository.countByBlockNumber(blockNumber))
    }

    override fun getHistory(): Result<List<TransactionHistoryVO>?> {

        val pageable: Pageable = PageRequest.of(0, historySize.toInt(), Sort.Direction.DESC, FIELD_NAME_CREATED_AT)

        val inflationMongoDOList: List<InflationMongoDO?> = inflationMongoRepository.findAll(pageable).content

        val transactionList: List<TransactionHistoryVO> = inflationMongoDOList.stream().map { inflationMongoDO ->
            val transactionHistoryVO = TransactionHistoryVO().apply {
                this.date = inflationMongoDO?.createdAt?.let { MathUtils.convertTimeZone(it) }
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

    private fun getAccountIsContractMap(transactionMongoDOList: List<TransactionMongoDO?>): Map<String, Boolean> {
        val addressList = transactionMongoDOList.flatMap { listOf(it?.from, it?.to) }
            .filterNotNull()
            .distinct()
        val addressDOList = accountRepository.findByAddressIn(addressList)

        return addressDOList.associate { it.address!! to (it.isContract == 1) }
    }
}
