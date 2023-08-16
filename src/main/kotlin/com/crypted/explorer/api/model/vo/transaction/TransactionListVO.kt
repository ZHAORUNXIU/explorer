package com.crypted.explorer.api.model.vo.transaction

import com.crypted.explorer.api.model.vo.account.AccountVO
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TransactionListVO : Serializable {
    @Schema(description = "txHash", type = "string")
    var txHash: String? = null
    @Schema(description = "method", type = "string")
    var method: String? = null
    @Schema(description = "blockNumber", type = "integer")
    var blockNumber: Long? = 0L
    @Schema(description = "blockTimestamp", type = "integer", format = "second")
    var blockTimestamp: Long? = 0
    @Schema(description = "fromAccount", type = "com.crypted.explorer.api.model.vo.account.AccountVO")
    var from: AccountVO? = null
    @Schema(description = "toAccount", type = "com.crypted.explorer.api.model.vo.account.AccountVO")
    var to: AccountVO? = null
    @Schema(description = "value", type = "string")
    var value: String? = null
    @Schema(description = "txFee", type = "string")
    var txFee: String? = null
    @Schema(description = "symbol", type = "string")
    var symbol: String? = null
    @Schema(description = "status 0-failure 1-success null-pending", type = "Int")
    var status: Int? = null

}