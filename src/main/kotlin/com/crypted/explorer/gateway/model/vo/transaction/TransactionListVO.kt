package com.crypted.explorer.gateway.model.vo.transaction

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TransactionListVO : Serializable {
    var txHash: String? = null
    var method: String? = null
    var blockNumber: Int? = 0
    var timestamp: Long? = 0
    var from: String? = null
    var to: String? = null
    var value: String? = null
    var txFee: String? = null
    var symbol: String? = null
    @Schema(description = "status 0-failure 1-success null-pending", type = "Int", )
    var status: Int? = null
}