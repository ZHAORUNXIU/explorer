package com.crypted.explorer.gateway.model.vo.transaction

import java.io.Serializable

class TransactionListVO : Serializable {
    var txHash: String? = null
    var method: String? = null
    var blockNumber: Int? = 0
    var timestamp: Long? = 0
    var from: String? = null
    var to: String? = null
    var value: String? = null
    var txFee: String? = null
    var symbol: String? = null
    var status: Int? = 1
}