package com.crypted.explorer.gateway.model.vo.transaction

import java.io.Serializable

class TransactionHistoryVO : Serializable {
    var date: String? = null
    var price: String? = null
    var count: Int? = 0
}