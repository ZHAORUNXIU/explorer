package com.crypted.explorer.gateway.model.resp.token

import com.crypted.explorer.api.model.vo.account.AccountVO
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TokenInfoResp: AccountVO(), Serializable {
    @Schema(description = "name", type = "string")
    var name: String? = null
    @Schema(description = "symbol", type = "string")
    var symbol: String? = null
    @Schema(description = "totalSupply", type = "string", format = "wei")
    var totalSupply: String? = null
    @Schema(description = "totalTransfer", type = "integer")
    var totalTransfer: Int? = 0
    @Schema(description = "officialSite", type = "string")
    var officialSite: String? = null
    @Schema(description = "imageUrl", type = "string")
    var imageUrl: String? = null
}