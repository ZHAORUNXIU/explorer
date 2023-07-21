package com.crypted.explorer.gateway.model.resp.transaction

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TransactionInfoResp : Serializable {
    @Schema(description = "txHash", type = "string")
    var txHash: String? = null
    @Schema(description = "blockNumber", type = "integer")
    var blockNumber: Int? = 0
    @Schema(description = "timestamp", type = "integer", format = "second")
    var timestamp: Long? = 0
    @Schema(description = "from", type = "string")
    var from: String? = null
    @Schema(description = "to", type = "string")
    var to: String? = null
    @Schema(description = "value", type = "string", format = "wei")
    var value: String? = null
    @Schema(description = "txFee", type = "string", format = "wei")
    var txFee: String? = null
    @Schema(description = "symbol", type = "string")
    var symbol: String? = null
    @Schema(description = "status 0-failure 1-success null-pending", type = "Int", )
    var status: Int? = null
}