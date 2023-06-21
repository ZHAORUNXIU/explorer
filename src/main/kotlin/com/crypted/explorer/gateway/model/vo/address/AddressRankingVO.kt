package com.crypted.explorer.gateway.model.vo.address

import java.io.Serializable

class AddressRankingVO : Serializable {
    var address: String? = null
    var isContract: Boolean? = null
    var balance: String? = null
    var symbol: String? = null
    var percentage: String? = null
    var txCount: Int? = null
}