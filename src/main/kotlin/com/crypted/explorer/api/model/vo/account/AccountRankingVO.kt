package com.crypted.explorer.api.model.vo.account

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class AccountRankingVO: AccountVO(), Serializable {
    @Schema(description = "balance", type = "String", format = "wei")
    var balance: String? = null
    @Schema(description = "symbol", type = "String")
    var symbol: String? = null
    @Schema(description = "percentage", type = "String")
    var percentage: String? = null
    @Schema(description = "txCount", type = "integer")
    var txCount: Int? = 0
}