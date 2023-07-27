package com.crypted.explorer.gateway.model.vo.token

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TokenTransferListVO : Serializable {
    @Schema(description = "txHash", type = "string")
    var txHash: String? = null
    @Schema(description = "method", type = "string")
    var method: String? = null
    @Schema(description = "blockNumber", type = "integer")
    var blockNumber: Int? = 0
    @Schema(description = "timestamp", type = "integer", format = "second")
    var timestamp: Long? = 0
    @Schema(description = "from", type = "string")
    var from: String? = null
    @Schema(description = "to", type = "string")
    var to: String? = null
    @Schema(description = "value", type = "string")
    var value: String? = null
    @Schema(description = "symbol", type = "string")
    var symbol: String? = null
}