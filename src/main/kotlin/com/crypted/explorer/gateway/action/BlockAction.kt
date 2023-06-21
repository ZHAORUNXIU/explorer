package com.crypted.explorer.gateway.action

import com.crypted.explorer.api.service.block.BlockService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Log
import com.crypted.explorer.gateway.model.resp.address.AddressRankingResp
import com.crypted.explorer.gateway.model.resp.block.BlockInfoResp
import com.crypted.explorer.gateway.model.resp.block.BlockListResp
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
    fun getlist(@RequestParam(required = true) @NotNull @Min(0) pageNumber: Int,
             @RequestParam(required = true) @NotNull @Min(0) pageSize: Int): Result<BlockListResp?> {

        LOG.info(Log.format("success", Log.kv("api", "block/list")))

        var result: Result<BlockListResp?> = blockService!!.getListByPage(pageNumber, pageSize)

        return Result.success(result.data)
    }

    @GetMapping("/info")
    fun getInfoByNumber(@RequestParam(required = true) @NotNull @Min(0) number: Int): Result<BlockInfoResp?> {
        LOG.info(Log.format("success", Log.kv("api", "block/info")))
        return Result.success(null)
    }
}
