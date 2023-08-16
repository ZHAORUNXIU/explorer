package com.crypted.explorer.api.model.vo.account

import com.crypted.explorer.api.model.vo.account.AccountVO
import com.crypted.explorer.api.model.vo.account.TokenHoldingVO
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class AccountInfoVO: AccountVO(), Serializable {
    var balance: String? = null
    var symbol: String? = null
}