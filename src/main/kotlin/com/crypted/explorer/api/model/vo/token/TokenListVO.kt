package com.crypted.explorer.api.model.vo.token

import com.crypted.explorer.api.model.vo.account.AccountVO
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TokenListVO: AccountVO(), Serializable {
    @Schema(description = "symbol", type = "string")
    var symbol: String? = null
    @Schema(description = "name", type = "string")
    var name: String? = null
    @Schema(description = "imageUrl", type = "string")
    var imageUrl: String? = null
}