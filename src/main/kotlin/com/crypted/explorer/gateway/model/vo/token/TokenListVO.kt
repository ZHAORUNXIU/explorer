package com.crypted.explorer.gateway.model.vo.token

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TokenListVO : Serializable {
    @Schema(description = "address", type = "string")
    var address: String? = null
    @Schema(description = "symbol", type = "string")
    var symbol: String? = null
    @Schema(description = "name", type = "string")
    var name: String? = null
    @Schema(description = "imageUrl", type = "string")
    var imageUrl: String? = null
}