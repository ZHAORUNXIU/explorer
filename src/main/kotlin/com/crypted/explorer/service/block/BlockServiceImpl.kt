package com.crypted.explorer.service.block

import com.crypted.explorer.api.model.domain.block.BlockMongoDO
import com.crypted.explorer.api.model.domain.block.InflationRatioDO
import com.crypted.explorer.api.service.block.BlockService
import com.crypted.explorer.api.service.transaction.TransactionService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.repository.MongoUtils
import com.crypted.explorer.common.util.MathUtils
import com.crypted.explorer.gateway.model.resp.block.BlockInfoResp
import com.crypted.explorer.gateway.model.resp.block.BlockListResp
import com.crypted.explorer.gateway.model.vo.block.BlockListVO
import com.crypted.explorer.service.account.AccountServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*


@Service
class BlockServiceImpl(
    private val mongoUtils: MongoUtils,
    private val blockMongoRepository: BlockMongoRepository,
    private val inflationRatioMongoRepository: InflationRatioMongoRepository,
    private val transactionService: TransactionService
) : BlockService {

    companion object {
        private val LOG = LoggerFactory.getLogger(BlockServiceImpl::class.java)
    }

    private val SORT_BY_BLOCK_NUMBER = "number"

    private val COLLECTION_NAME_BLOCKS = "blocks"

    @Value("\${block.symbol}")
    private lateinit var symbol: String

    override fun getListByPage(pageNumber: Int, pageSize: Int): Result<BlockListResp?> {

        val pageable: Pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.DESC, SORT_BY_BLOCK_NUMBER)
        val blockMongoDOList: List<BlockMongoDO> = mongoUtils.getByPage(pageable, BlockMongoDO::class)

        val blockList: List<BlockListVO?> = blockMongoDOList.stream().map { blockMongoDO ->
            val blockListVO = BlockListVO()
            blockListVO.blockNumber = blockMongoDO!!.number
            blockListVO.timestamp = blockMongoDO.timestamp.toString()
//            blockListVO.txCount = transactionService!!.getTransactionAmountByBlockNumber(blockMongoDO.number).data
            blockListVO.txCount = blockMongoDO.transactionCount
            blockListVO.blockReward = blockMongoDO.blockReward
            blockListVO.symbol = this@BlockServiceImpl.symbol
            blockListVO
        }.toList()

//        val totalBlock: Int = blockMongoRepository!!.count().toInt()
        val totalBlock: Int = mongoUtils.getCountWithNativeQuery(COLLECTION_NAME_BLOCKS)

//        val totalBlock: Int = blockMongoRepository!!.findTopByOrderByNumberDesc().number


        val blockListResp = BlockListResp().apply {
            this.totalPage = MathUtils.ceilDiv(totalBlock, pageSize)
            this.totalBlock = totalBlock
            this.blockList = blockList
        }

        return Result.success(blockListResp)
    }

    override fun getInfoByBlockNumber(blockNumber: Int): Result<BlockInfoResp?> {

        val blockMongoDO: BlockMongoDO = blockMongoRepository.findByNumber(blockNumber)

        val blockInfoResp = BlockInfoResp().apply {
            this.blockNumber = blockMongoDO.number
            this.timestamp = blockMongoDO.timestamp.toString()
            this.txCount = transactionService.getTransactionAmountByBlockNumber(blockMongoDO.number).data
            this.blockReward = blockMongoDO.blockReward
            this.symbol = this@BlockServiceImpl.symbol
            this.latestBlock = blockMongoRepository.findTopByOrderByNumberDesc().number
        }

        return Result.success(blockInfoResp)
    }

    override fun getTotalBlockReward(): Result<String> {

        val inflationRatioDOList: List<InflationRatioDO> = inflationRatioMongoRepository.findAll()
        val latestBlockNumber = BigInteger(blockMongoRepository.findTopByOrderByNumberDesc().number.toString())

        val sortedList = inflationRatioDOList.sortedBy { it.blockNumber }

        var accumulatedBlockReward = BigDecimal.ONE
            .multiply(BigDecimal("5000000000"))
            .scaleByPowerOfTen(18).toBigInteger()

//        var accumulatedBlockReward = BigInteger("5000000000")
        //BigInteger.ZERO
        var previousBlockNumber = BigInteger.ONE
        var staticBlockReward = BigInteger.ZERO

        for (inflationRatioDO in sortedList) {
            val currentBlockNumber = BigInteger(inflationRatioDO.blockNumber.toString())

            accumulatedBlockReward += staticBlockReward.multiply(currentBlockNumber.subtract(previousBlockNumber))

            previousBlockNumber = currentBlockNumber
            staticBlockReward = BigInteger(inflationRatioDO.staticBlockReward ?: "0")
        }

        if (latestBlockNumber > previousBlockNumber)
            accumulatedBlockReward += staticBlockReward.multiply(
                latestBlockNumber.subtract(previousBlockNumber).add(BigInteger.ONE)
            )

        return Result.success(accumulatedBlockReward.toString())
    }
}

