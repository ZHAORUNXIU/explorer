package com.crypted.explorer.gateway.model.resp.token

import java.io.Serializable

class TokenInfoResp : Serializable {
    var name: String? = null
    var symbol: String? = null
    var address: String? = null
    var totalSupply: String? = null
    var totalTransfer: Int? = 0
    var officialSite: String? = null
    var imageUrl: String? = null
}