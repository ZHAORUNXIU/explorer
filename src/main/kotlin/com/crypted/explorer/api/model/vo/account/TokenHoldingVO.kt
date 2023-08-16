package com.crypted.explorer.api.model.vo.account

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TokenHoldingVO : Serializable {
    @Schema(description = "name", type = "string")
    var name: String? = null
    @Schema(description = "tokenSymbol", type = "string")
    var tokenSymbol: String? = null
    @Schema(description = "tokenBalance", type = "string", format = "wei")
    var tokenBalance: String? = null
    @Schema(description = "imageUrl", type = "string")
    var imageUrl: String? = null
}