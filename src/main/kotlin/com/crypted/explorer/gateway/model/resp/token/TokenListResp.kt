package com.crypted.explorer.gateway.model.resp.token

import com.crypted.explorer.gateway.model.vo.token.TokenListVO
import java.io.Serializable

class TokenListResp : Serializable {
    var totalPage: Int? = 0
    var totalToken: Int? = 0
    var tokenList: List<TokenListVO?> = emptyList()
}