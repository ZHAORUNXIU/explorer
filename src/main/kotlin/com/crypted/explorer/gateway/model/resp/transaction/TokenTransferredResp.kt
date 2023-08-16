package com.crypted.explorer.gateway.model.resp.transaction

import com.crypted.explorer.api.model.vo.account.AccountVO
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TokenTransferredVO : Serializable {
    @Schema(description = "txHash", type = "string")
    var txHash: String? = null
    @Schema(description = "fromAccount", type = "com.crypted.explorer.api.model.vo.account.AccountVO")
    var from: AccountVO? = null
    @Schema(description = "toAccount", type = "com.crypted.explorer.api.model.vo.account.AccountVO")
    var to: AccountVO? = null
    @Schema(description = "value", type = "string", format = "wei")
    var value: String? = null
    @Schema(description = "name", type = "string")
    var name: String? = null
    @Schema(description = "symbol", type = "string")
    var symbol: String? = null
}