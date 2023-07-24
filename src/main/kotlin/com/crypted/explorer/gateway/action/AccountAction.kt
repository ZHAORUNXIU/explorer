package com.crypted.explorer.gateway.action

import com.crypted.explorer.api.service.account.AccountService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Log
import com.crypted.explorer.gateway.model.resp.account.AccountInfoResp
import com.crypted.explorer.gateway.model.resp.account.AccountRankingResp
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
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@RestController
@RequestMapping(value = ["/v1/account"])
@Validated
@Component
@Tag(name = "Account Service API", description = "API endpoints related to account operations")
class AccountAction(private val accountService: AccountService) {

    companion object {
        private val LOG = LoggerFactory.getLogger(AccountAction::class.java)
    }

    @GetMapping("/ranking")
    @Operation(summary = "Get ranking", description = "Retrieve account ranking based on the provided pageNumber and pageSize")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Resource Not Found", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "500", description = "System Error", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "501", description = "Invalid Request", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "502", description = "Invalid Parameter", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "504", description = "Missing parameter", content = [Content(schema = Schema(implementation = Result::class))])
    fun getRanking(@Parameter(description = "pageNumber", required = true) @RequestParam(required = true) @NotNull @Min(0) pageNumber: Int,
                   @Parameter(description = "pageSize", required = true) @RequestParam(required = true) @NotNull @Min(0) pageSize: Int): Result<AccountRankingResp?> {

        LOG.info(Log.format("success", Log.kv("api", "account/ranking")))

        val result: Result<AccountRankingResp?> = accountService.getRankingByPage(pageNumber, pageSize)

        return Result.success(result.data)
    }

    /**
     * EOA or CA
     */
    @GetMapping("/{address}")
    @Operation(summary = "Get info by address", description = "Retrieve account info based on the provided address")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Resource Not Found", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "500", description = "System Error", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "501", description = "Invalid Request", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "502", description = "Invalid Parameter", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "504", description = "Missing parameter", content = [Content(schema = Schema(implementation = Result::class))])
    fun getInfoByAddress(@Parameter(description = "EOA address", required = true) @PathVariable("address") @NotNull address: String): Result<AccountInfoResp?> {

        LOG.info(Log.format("success", Log.kv("api", "account/")))

        // EOA
//        if (accountService!!.checkAccountIsContract(address).data == true)
//            return Result.failure(AccountCode.NOT_EXTERNALLY_OWNED_ACCOUNT.code, AccountCode.NOT_EXTERNALLY_OWNED_ACCOUNT.message)

        val result: Result<AccountInfoResp?> = accountService.getInfoByAddress(address)

        return Result.success(result.data)
    }

}
