package com.crypted.explorer.gateway.action

import com.crypted.explorer.api.service.account.AccountService
import com.crypted.explorer.api.service.token.TokenService
import com.crypted.explorer.api.service.transaction.TransactionService
import com.crypted.explorer.common.constant.AccountCode
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Log
import com.crypted.explorer.gateway.model.resp.token.TokenInfoResp
import com.crypted.explorer.gateway.model.resp.token.TokenListResp
import com.crypted.explorer.api.model.vo.token.TokenInfoVO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@RestController
@RequestMapping(value = ["/v1/token"])
@Validated
@Component
@Tag(name = "Token Service API", description = "API endpoints related to token operations")
class TokenAction(
    private val tokeService: TokenService,
    private val transactionService:TransactionService
    ) {

    companion object {
        private val LOG = LoggerFactory.getLogger(TokenAction::class.java)
    }

    @GetMapping("/list")
    @Operation(summary = "Get list", description = "Retrieve token list based on the provided pageNumber and pageSize")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(
        responseCode = "500",
        description = "System Error",
        content = [Content(schema = Schema(implementation = Result::class))]
    )
    @ApiResponse(
        responseCode = "501",
        description = "Invalid Request",
        content = [Content(schema = Schema(implementation = Result::class))]
    )
    @ApiResponse(
        responseCode = "502",
        description = "Invalid Parameter",
        content = [Content(schema = Schema(implementation = Result::class))]
    )
    @ApiResponse(
        responseCode = "504",
        description = "Missing parameter",
        content = [Content(schema = Schema(implementation = Result::class))]
    )
    fun getList(
        @Parameter(
            description = "pageNumber",
            required = true
        ) @RequestParam(required = true) @NotNull @Min(0) pageNumber: Int,
        @Parameter(
            description = "pageSize",
            required = true
        ) @RequestParam(required = true) @NotNull @Min(0) pageSize: Int
    ): Result<TokenListResp?> {

        LOG.info(Log.format("success", Log.kv("api", "/token/list")))

        val result: Result<TokenListResp?> = tokeService.getListByPage(pageNumber, pageSize)

        return Result.success(result.data)
    }

    @GetMapping("/{contractAddress}")
    @Operation(
        summary = "Get info by contract address",
        description = "Retrieve contract account details based on the provided contract address"
    )
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(
        responseCode = "404",
        description = "Resource Not Found",
        content = [Content(schema = Schema(implementation = Result::class))]
    )
    @ApiResponse(
        responseCode = "500",
        description = "System Error",
        content = [Content(schema = Schema(implementation = Result::class))]
    )
    @ApiResponse(
        responseCode = "501",
        description = "Invalid Request",
        content = [Content(schema = Schema(implementation = Result::class))]
    )
    @ApiResponse(
        responseCode = "502",
        description = "Invalid Parameter",
        content = [Content(schema = Schema(implementation = Result::class))]
    )
    @ApiResponse(
        responseCode = "504",
        description = "Missing parameter",
        content = [Content(schema = Schema(implementation = Result::class))]
    )
    fun getInfoByContractAddress(
        @Parameter(
            description = "CA address",
            required = true
        ) @PathVariable("contractAddress") @NotNull contractAddress: String
    ): Result<TokenInfoResp?> {

        LOG.info(Log.format("success", Log.kv("api", "token/")))

        val tokenInfoVO: TokenInfoVO = tokeService.getInfoByContractAddress(contractAddress).data!!
        val tokenInfoResp = TokenInfoResp()
        BeanUtils.copyProperties(tokenInfoVO, tokenInfoResp)
        tokenInfoResp.totalTransfer = tokenInfoVO.type?.let { type ->
            tokenInfoVO.address?.let { address ->
                transactionService.getTotalTransfers(type, address).data
            }
        }

        return Result.success(tokenInfoResp)
    }

//    @GetMapping("/transfers")
//    @Operation(summary = "Get transfer list", description = "Retrieve transfer list based on the provided conditions")
//    @ApiResponse(responseCode = "200", description = "Success")
//    @ApiResponse(responseCode = "404", description = "Resource Not Found", content = [Content(schema = Schema(implementation = Result::class))])
//    @ApiResponse(responseCode = "500", description = "System Error", content = [Content(schema = Schema(implementation = Result::class))])
//    @ApiResponse(responseCode = "501", description = "Invalid Request", content = [Content(schema = Schema(implementation = Result::class))])
//    @ApiResponse(responseCode = "502", description = "Invalid Parameter", content = [Content(schema = Schema(implementation = Result::class))])
//    @ApiResponse(responseCode = "504", description = "Missing parameter", content = [Content(schema = Schema(implementation = Result::class))])
//    fun getTransferList(@Parameter(description = "tokenAddress", required = true,) @RequestParam(required = true) @NotNull tokenAddress: String,
//                @Parameter(description = "pageNumber", required = true) @RequestParam(required = true) @NotNull @Min(1) pageNumber: Int,
//                @Parameter(description = "pageSize", required = true) @RequestParam(required = true) @NotNull @Min(0) pageSize: Int): Result<TokenTransferListResp?> {
//
//        LOG.info(Log.format("success", Log.kv("api", "token/transfers")))
//
//        val result: Result<TokenTransferListResp?> = tokeService.getTransferListByPage(tokenAddress, pageNumber, pageSize)
//
//        return Result.success(result.data)
//    }

}
