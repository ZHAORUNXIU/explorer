package com.crypted.explorer.gateway.model.resp.transaction

import java.io.Serializable

class TransactionInfoResp : Serializable {
    var txHash: String? = null
    var blockNumber: Int? = 0
    var timestamp: Int? = 0
    var from: String? = null
    var to: String? = null
    var value: String? = null
    var txFee: String? = null
    var symbol: String? = null
}