package com.crypted.explorer.gateway.model.resp.token

import com.crypted.explorer.gateway.model.vo.token.TokenListVO
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TokenListResp : Serializable {
    @Schema(description = "totalPage", type = "integer")
    var totalPage: Int? = 0
    @Schema(description = "totalToken", type = "integer")
    var totalToken: Int? = 0
    @Schema(description = "tokenList", type = "array")
    var tokenList: List<TokenListVO?> = emptyList()
}