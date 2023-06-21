package com.crypted.explorer.gateway.model.vo.transaction

import java.io.Serializable

class TransactionListVO : Serializable {
    var txHash: String? = null
    var method: String? = null
    var blockNumber: Int? = null
    var timestamp: Int? = null
    var from: String? = null
    var to: String? = null
    var value: String? = null
    var txFee: String? = null
    var symbol: String? = null
}