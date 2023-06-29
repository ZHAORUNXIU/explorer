package com.crypted.explorer.gateway.action

import com.crypted.explorer.api.service.block.BlockService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Log
import com.crypted.explorer.gateway.model.resp.block.BlockInfoResp
import com.crypted.explorer.gateway.model.resp.block.BlockListResp
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@RestController
@RequestMapping(value = ["/v1/block"])
@Validated
@Component
class BlockAction {

    companion object {
        private val LOG = LoggerFactory.getLogger(BlockAction::class.java)
    }

    @Resource
    private val blockService: BlockService? = null

    @GetMapping("/list")
    fun getList(@RequestParam(required = true) @NotNull @Min(0) pageNumber: Int,
             @RequestParam(required = true) @NotNull @Min(0) pageSize: Int): Result<BlockListResp?> {

        LOG.info(Log.format("success", Log.kv("api", "block/list")))

        val result: Result<BlockListResp?> = blockService!!.getListByPage(pageNumber, pageSize)

        return Result.success(result.data)
    }

    @GetMapping("/{blockNumber}")
    fun getInfoByBlockNumber(@PathVariable("blockNumber") @NotNull @Min(0) blockNumber: Int): Result<BlockInfoResp?> {

        LOG.info(Log.format("success", Log.kv("api", "block/")))

        val result: Result<BlockInfoResp?> = blockService!!.getInfoByBlockNumber(blockNumber)

        return Result.success(result.data)
    }
}
