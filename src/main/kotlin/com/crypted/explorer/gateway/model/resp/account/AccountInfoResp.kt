package com.crypted.explorer.gateway.model.resp.account

import com.crypted.explorer.gateway.model.vo.token.TokenVO
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class AccountInfoResp : Serializable {
    @Schema(description = "address", type = "string")
    var address: String? = null
    @Schema(description = "balance", type = "string", format = "wei")
    var balance: String? = null
    @Schema(description = "symbol", type = "string")
    var symbol: String? = null
    @Schema(description = "tokenCount", type = "integer")
    var tokenCount: Int? = 0
    @Schema(description = "tokenHoldings", type = "array")
    var tokenHoldings: List<TokenVO>? = null
}