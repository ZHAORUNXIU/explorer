package com.crypted.explorer.gateway.model.resp.transaction

import com.crypted.explorer.gateway.model.vo.transaction.TransactionListVO
import java.io.Serializable

class TransactionListResp : Serializable {
    var totalPage: Int? = null
    var totalTx: Int? = null
    var transactionList: List<TransactionListVO>? = null
}