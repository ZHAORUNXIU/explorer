package com.crypted.explorer.gateway.model.resp.transaction

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TransactionTokenInfoResp : Serializable {
    @Schema(description = "txHash", type = "string")
    var txHash: String? = null
    @Schema(description = "from", type = "string")
    var from: String? = null
    @Schema(description = "to", type = "string")
    var to: String? = null
    @Schema(description = "value", type = "string", format = "wei")
    var value: String? = null
    @Schema(description = "name", type = "string")
    var name: String? = null
    @Schema(description = "symbol", type = "string")
    var symbol: String? = null
}