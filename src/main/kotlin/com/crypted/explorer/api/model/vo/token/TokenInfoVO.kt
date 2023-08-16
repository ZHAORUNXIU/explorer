package com.crypted.explorer.api.model.vo.token

import com.crypted.explorer.api.model.vo.account.AccountVO
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TokenInfoVO : AccountVO(), Serializable {
    var name: String? = null
    var symbol: String? = null
    var type: String? = null
    var totalSupply: String? = null
    var officialSite: String? = null
    var imageUrl: String? = null
}