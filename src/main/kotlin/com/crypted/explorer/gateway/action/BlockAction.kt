package com.crypted.explorer.gateway.action

import com.crypted.explorer.api.service.block.BlockService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Log
import com.crypted.explorer.gateway.model.resp.block.BlockInfoResp
import com.crypted.explorer.gateway.model.resp.block.BlockListResp
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
@RequestMapping(value = ["/v1/block"])
@Validated
@Component
@Tag(name = "Block Service API", description = "API endpoints related to block operations")
class BlockAction(private val blockService: BlockService)  {

    companion object {
        private val LOG = LoggerFactory.getLogger(BlockAction::class.java)
    }

    @GetMapping("/list")
    @Operation(summary = "Get list", description = "Retrieve block list based on the provided pageNumber and pageSize")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Resource Not Found", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "500", description = "System Error", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "501", description = "Invalid Request", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "502", description = "Invalid Parameter", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "504", description = "Missing parameter", content = [Content(schema = Schema(implementation = Result::class))])
    fun getList(@Parameter(description = "pageNumber", required = true) @RequestParam(required = true) @NotNull @Min(0) pageNumber: Int,
                @Parameter(description = "pageNumber", required = true) @RequestParam(required = true) @NotNull @Min(0) pageSize: Int): Result<BlockListResp?> {

        LOG.info(Log.format("success", Log.kv("api", "block/list")))

        val result: Result<BlockListResp?> = blockService.getListByPage(pageNumber, pageSize)

        return Result.success(result.data)
    }

    @GetMapping("/{blockNumber}")
    @Operation(summary = "Get info by block number", description = "Retrieve block info based on the provided block number")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Resource Not Found", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "500", description = "System Error", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "501", description = "Invalid Request", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "502", description = "Invalid Parameter", content = [Content(schema = Schema(implementation = Result::class))])
    @ApiResponse(responseCode = "504", description = "Missing parameter", content = [Content(schema = Schema(implementation = Result::class))])
    fun getInfoByBlockNumber(@Parameter(description = "blockNumber", required = true) @PathVariable("blockNumber") @NotNull @Min(0) blockNumber: Int): Result<BlockInfoResp?> {

        LOG.info(Log.format("success", Log.kv("api", "block/")))

        val result: Result<BlockInfoResp?> = blockService.getInfoByBlockNumber(blockNumber)

        return Result.success(result.data)
    }
}
