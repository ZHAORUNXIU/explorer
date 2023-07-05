package com.crypted.explorer.gateway.model.resp.block

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class BlockInfoResp : Serializable {
    @Schema(description = "blockNumber", type = "integer")
    var blockNumber: Int? = 0
    @Schema(description = "timestamp", type = "string")
    var timestamp: String? = null
    @Schema(description = "txCount", type = "integer")
    var txCount: Int? = 0
    @Schema(description = "blockReward", type = "string")
    var blockReward: String? = null
    @Schema(description = "symbol", type = "string")
    var symbol: String? = null
    @Schema(description = "latestBlock", type = "integer")
    var latestBlock: Int? = 0
}