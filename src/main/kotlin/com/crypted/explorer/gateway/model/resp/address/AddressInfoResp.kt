package com.crypted.explorer.gateway.model.resp.address

import com.crypted.explorer.gateway.model.vo.address.TokenVO
import java.io.Serializable

class AddressInfoResp : Serializable {
    var address: String? = null
    var balance: String? = null
    var symbol: String? = null
    var tokenCount: Int? = 0
    var tokenHoldings: List<TokenVO?> = emptyList()
}