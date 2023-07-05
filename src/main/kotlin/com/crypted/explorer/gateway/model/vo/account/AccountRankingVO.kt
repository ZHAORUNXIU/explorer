package com.crypted.explorer.gateway.model.vo.account

import java.io.Serializable

class AccountRankingVO : Serializable {
    var address: String? = null
    var isContract: Boolean? = null
    var balance: String? = null
    var symbol: String? = null
    var percentage: String? = null
    var txCount: Int? = 0
}