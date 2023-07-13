package com.crypted.explorer.service.transaction

import com.crypted.explorer.api.model.domain.block.BlockMongoDO
import com.crypted.explorer.api.model.domain.transaction.InflationMongoDO
import com.crypted.explorer.api.model.domain.transaction.TransactionMongoDO
import com.crypted.explorer.api.service.transaction.TransactionService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.repository.MongoUtils
import com.crypted.explorer.common.util.MathUtils
import com.crypted.explorer.gateway.model.resp.transaction.TransactionInfoResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionListResp
import com.crypted.explorer.gateway.model.vo.transaction.TransactionHistoryVO
import com.crypted.explorer.gateway.model.vo.transaction.TransactionListVO
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import javax.annotation.Resource
import kotlin.streams.toList

@Service
class TransactionServiceImpl(private val mongoUtils: MongoUtils) : TransactionService {

    companion object {
        private val LOG = LoggerFactory.getLogger(TransactionServiceImpl::class.java)
    }

    private val SORT_BY_CREATED_AT = "createdAt"

    @Value("\${transaction.symbol}")
    private lateinit var symbol: String

    @Value("\${transaction.history.size}")
    private lateinit var historySize: String

    @Resource
    private val transactionMongoRepository: TransactionMongoRepository? = null

    @Resource
    private val inflationMongoRepository : InflationMongoRepository? = null
    override fun getListByPage(fromAddress: String?, toAddress: String?, blockNumber: Int?, pageNumber: Int, pageSize: Int): Result<TransactionListResp?> {

        val pageable: Pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.DESC, SORT_BY_CREATED_AT)

        val transactionMongoDOList: List<TransactionMongoDO?>
        val totalTx: Int
        // get txs by from and to
        if (!fromAddress.isNullOrBlank() && !toAddress.isNullOrBlank() && blockNumber == null) {
            transactionMongoDOList = transactionMongoRepository!!.findByFromOrTo(fromAddress, toAddress, pageable).content
            totalTx = transactionMongoRepository.countByFromOrTo(fromAddress, toAddress)
        }
        // get txs by to
        else if (fromAddress.isNullOrBlank() && !toAddress.isNullOrBlank() && blockNumber == null) {
            transactionMongoDOList = transactionMongoRepository!!.findByTo(toAddress, pageable).content
            totalTx = transactionMongoRepository.countByTo(toAddress)
        }
        // get txs by block number
        else if (fromAddress.isNullOrBlank() && toAddress.isNullOrBlank() && blockNumber != null) {
            transactionMongoDOList = transactionMongoRepository!!.findByBlockNumber(blockNumber, pageable).content
            totalTx = transactionMongoRepository.countByBlockNumber(blockNumber)
        }
        // get all txs
        else {
            transactionMongoDOList = mongoUtils.getByPage(pageable, TransactionMongoDO::class)
            totalTx = transactionMongoRepository!!.count().toInt()
        }


        val transactionList: List<TransactionListVO?> = transactionMongoDOList.stream().map { transactionMongoDO ->
            val transactionListVO = TransactionListVO()
            transactionListVO.status = transactionMongoDO?.status
            transactionListVO.txHash = transactionMongoDO!!.hash
            transactionListVO.method = transactionMongoDO.functionName?: transactionMongoDO.functionSignature
            transactionListVO.blockNumber = transactionMongoDO.blockNumber
            transactionListVO.timestamp = transactionMongoDO.createdAt?.time?.div(1000)
            transactionListVO.from = transactionMongoDO.from
            transactionListVO.to = transactionMongoDO.to
            transactionListVO.value =  transactionMongoDO.value
            transactionListVO.txFee = transactionMongoDO.fee?.let { MathUtils.convertWeiToEther(it) }
            transactionListVO.symbol =  this@TransactionServiceImpl.symbol
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

        val transactionMongoDO: TransactionMongoDO? = transactionMongoRepository!!.findByHash(txHash)

        val transactionInfoResp = TransactionInfoResp().apply {
            this.txHash = transactionMongoDO?.hash
            this.blockNumber = transactionMongoDO?.blockNumber
            this.timestamp = transactionMongoDO?.createdAt?.time?.toInt()
            this.from = transactionMongoDO?.from
            this.to = transactionMongoDO?.to
            this.value = transactionMongoDO?.value
            this.txFee = transactionMongoDO?.fee?.let { MathUtils.convertWeiToEther(it) }
            this.symbol = this@TransactionServiceImpl.symbol
            this.status = transactionMongoDO?.status
        }

        return Result.success(transactionInfoResp)

    }


    override fun getTransactionAmountByBlockNumber(blockNumber: Int): Result<Int> {

        return Result.success(transactionMongoRepository!!.countByBlockNumber(blockNumber))
    }

    override fun getHistory(): Result<List<TransactionHistoryVO>?> {

        val pageable: Pageable = PageRequest.of(0, historySize.toInt(), Sort.Direction.DESC, SORT_BY_CREATED_AT)

        val inflationMongoDOList: List<InflationMongoDO?> = inflationMongoRepository!!.findAll(pageable).content

        val transactionList: List<TransactionHistoryVO> = inflationMongoDOList.stream().map { inflationMongoDO ->
            val transactionHistoryVO = TransactionHistoryVO().apply {
                this.date = inflationMongoDO?.createdAt?.let { MathUtils.convertTimeZone(it) }
                this.count = inflationMongoDO?.transactionCount
            }
            transactionHistoryVO
        }.toList()

        return Result.success(transactionList)
    }
}
