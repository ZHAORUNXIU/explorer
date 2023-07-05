package com.crypted.explorer.gateway.model.resp.block

import com.crypted.explorer.gateway.model.vo.block.BlockListVO
import io.swagger.v3.oas.annotations.media.Schema

import java.io.Serializable

class BlockListResp : Serializable {
    @Schema(description = "totalPage", type = "integer")
    var totalPage: Int? = 0
    @Schema(description = "totalBlock", type = "integer")
    var totalBlock: Int? = 0
    @Schema(description = "blockList", type = "array")
    var blockList: List<BlockListVO?> = emptyList()
}