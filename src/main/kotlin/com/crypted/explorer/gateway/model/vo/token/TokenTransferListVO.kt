package com.crypted.explorer.gateway.model.vo.token

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TokenTransferListVO : Serializable {
    @Schema(description = "txHash", type = "string")
    var txHash: String? = null
    @Schema(description = "method", type = "string")
    var method: String? = null
    @Schema(description = "blockNumber", type = "integer")
    var blockNumber: Long? = 0L
    @Schema(description = "blockTimestamp", type = "integer", format = "second")
    var blockTimestamp: Long? = 0
    @Schema(description = "from", type = "string")
    var from: String? = null
    @Schema(description = "fromIsContract", type = "boolean", example = "true")
    var fromIsContract: Boolean? = null
    @Schema(description = "to", type = "string")
    var to: String? = null
    @Schema(description = "toIsContract", type = "boolean", example = "true")
    var toIsContract: Boolean? = null
    @Schema(description = "value", type = "string")
    var value: String? = null
    @Schema(description = "symbol", type = "string")
    var symbol: String? = null

}