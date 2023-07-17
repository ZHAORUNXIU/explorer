package com.crypted.explorer.gateway.action

import com.crypted.explorer.api.service.transaction.TransactionService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Log
import com.crypted.explorer.gateway.model.resp.transaction.TransactionListResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionInfoResp
import com.crypted.explorer.gateway.model.vo.transaction.TransactionHistoryVO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@RestController
@RequestMapping(value = ["/v1/transaction"])
@Validated
@Component
@Tag(name = "Transaction Service API", description = "API endpoints related to transaction operations")
class TransactionAction {

    companion object {
        private val LOG = LoggerFactory.getLogger(TransactionAction::class.java)
    }

    @Resource
    private val transactionService: TransactionService? = null

    @GetMapping("/list")
    @Operation(summary = "Get list", description = "Retrieve transaction list based on the provided conditions")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "500", description = "System Error", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "501", description = "Invalid Request", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "502", description = "Invalid Parameter", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "504", description = "Missing parameter", content = [Content(schema = Schema(implementation = Result::class))])
    fun getList(@Parameter(description = "fromAddress", required = false, allowEmptyValue = true) @RequestParam(required = false) fromAddress: String?,
                @Parameter(description = "toAddress", required = false, allowEmptyValue = true) @RequestParam(required = false) toAddress: String?,
                @Parameter(description = "blockNumber", required = false, allowEmptyValue = true) @RequestParam(required = false) @Min(1) blockNumber: Int?,
                @Parameter(description = "status", required = false) @RequestParam(required = false) @Min(0) @Max(1) status: Int?,
                @Parameter(description = "pageNumber", required = true) @RequestParam(required = true) @NotNull @Min(1) pageNumber: Int,
                @Parameter(description = "pageSize", required = true) @RequestParam(required = true) @NotNull @Min(0) pageSize: Int): Result<TransactionListResp?> {

        LOG.info(Log.format("success", Log.kv("api", "transaction/list")))

        val result: Result<TransactionListResp?> = transactionService!!.getListByPage(fromAddress, toAddress, blockNumber, status, pageNumber, pageSize)

        return Result.success(result.data)
    }

    @GetMapping("/{txHash}")
    @Operation(summary = "Get info by txHash", description = "Retrieve transaction info based on the provided txHash")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "500", description = "System Error", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "501", description = "Invalid Request", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "502", description = "Invalid Parameter", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "504", description = "Missing parameter", content = [Content(schema = Schema(implementation = Result::class))])
    fun getInfoByTxHash(@Parameter(description = "txHash", required = true) @PathVariable("txHash") @NotNull txHash: String): Result<TransactionInfoResp?> {

        LOG.info(Log.format("success", Log.kv("api", "transaction/")))

        val result: Result<TransactionInfoResp?> = transactionService!!.getInfoByTxHash(txHash)

        return Result.success(result.data)
    }

    @GetMapping("/history")
    @Operation(summary = "Get history", description = "Retrieve transaction histories")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "500", description = "System Error", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "501", description = "Invalid Request", content = [Content(schema = Schema(implementation = Result::class))])
    fun getHistory(): Result<List<TransactionHistoryVO>?> {

        LOG.info(Log.format("success", Log.kv("api", "transaction/history")))

        val result: Result<List<TransactionHistoryVO>?> = transactionService!!.getHistory()

        return Result.success(result.data)
    }

}
