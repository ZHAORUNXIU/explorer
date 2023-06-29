package com.crypted.explorer.gateway.model.vo.token

import java.io.Serializable

class TokenListVO : Serializable {
    var address: String? = null
    var symbol: String? = null
    var name: String? = null
    var imageUrl: String? = null
}