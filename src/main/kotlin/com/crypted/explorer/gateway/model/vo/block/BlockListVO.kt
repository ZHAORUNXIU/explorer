package com.crypted.explorer.gateway.model.vo.block

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class BlockListVO : Serializable {
    @Schema(description = "blockReward", type = "integer")
    var blockNumber: Long? = 0L
    @Schema(description = "timestamp", type = "string", format = "second")
    var timestamp: String? = null
    @Schema(description = "txCount", type = "integer")
    var txCount: Int? = 0
    @Schema(description = "blockReward", type = "string", format = "wei")
    var blockReward: String? = null
    @Schema(description = "symbol", type = "string")
    var symbol: String? = null
}