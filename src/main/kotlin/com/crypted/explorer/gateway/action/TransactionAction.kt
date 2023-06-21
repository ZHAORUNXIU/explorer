package com.crypted.explorer.gateway.action

import com.crypted.explorer.api.service.transaction.TransactionService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Log
import com.crypted.explorer.gateway.model.resp.address.AddressRankingResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionHistoryResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionListResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionInfoResp
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
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
    fun getlist(@RequestParam(required = true) @NotNull @Min(0) pageNumber: Int,
             @RequestParam(required = true) @NotNull @Min(0) pageSize: Int): Result<TransactionListResp?> {

        LOG.info(Log.format("success", Log.kv("api", "transaction/list")))

        var result: Result<TransactionListResp?> = transactionService!!.getListByPage(pageNumber, pageSize)

        return Result.success(result.data)
    }

    @GetMapping("/info")
    fun getInfoByTxHash(@RequestParam(required = true) @NotNull txHash: String): Result<TransactionInfoResp?> {
        LOG.info(Log.format("success", Log.kv("api", "transaction/info")))
        return Result.success(null)
    }

    @GetMapping("/history")
    fun getHistory(): Result<TransactionHistoryResp?> {
        LOG.info(Log.format("success", Log.kv("api", "transaction/history")))
        return Result.success(null)
    }

}
