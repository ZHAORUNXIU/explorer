package com.crypted.explorer.gateway.model.vo.token

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TokenTransferVO : Serializable {
    var txHash: String? = null
    var from: String? = null
    var to: String? = null
    var value: String? = null
    var name: String? = null
    var symbol: String? = null
}