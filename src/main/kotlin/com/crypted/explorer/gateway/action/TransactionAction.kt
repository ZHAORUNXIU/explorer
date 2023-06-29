package com.crypted.explorer.gateway.action

import com.crypted.explorer.api.service.transaction.TransactionService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Log
import com.crypted.explorer.gateway.model.resp.transaction.TransactionListResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionInfoResp
import com.crypted.explorer.gateway.model.vo.transaction.TransactionHistoryVO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@RestController
@RequestMapping(value = ["/v1/transaction"])
@Validated
@Component
class TransactionAction {

    companion object {
        private val LOG = LoggerFactory.getLogger(TransactionAction::class.java)
    }

    @Resource
    private val transactionService: TransactionService? = null

    @GetMapping("/list")
    fun getList(@RequestParam(required = false) fromAddress: String?,
                @RequestParam(required = false) toAddress: String?,
                @RequestParam(required = false) @Min(1) blockNumber: Int?,
                @RequestParam(required = true) @NotNull @Min(1) pageNumber: Int,
                @RequestParam(required = true) @NotNull @Min(0) pageSize: Int): Result<TransactionListResp?> {

        LOG.info(Log.format("success", Log.kv("api", "transaction/list")))

        val result: Result<TransactionListResp?> = transactionService!!.getListByPage(fromAddress, toAddress, blockNumber, pageNumber, pageSize)

        return Result.success(result.data)
    }

    @GetMapping("/{txHash}")
    fun getInfoByTxHash(@PathVariable("txHash") @NotNull txHash: String): Result<TransactionInfoResp?> {

        LOG.info(Log.format("success", Log.kv("api", "transaction/")))

        val result: Result<TransactionInfoResp?> = transactionService!!.getInfoByTxHash(txHash)

        return Result.success(result.data)
    }

    @GetMapping("/history")
    fun getHistory(): Result<List<TransactionHistoryVO>?> {

        LOG.info(Log.format("success", Log.kv("api", "transaction/history")))

        val result: Result<List<TransactionHistoryVO>?> = transactionService!!.getHistory()

        return Result.success(result.data)
    }

}
