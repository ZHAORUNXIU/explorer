package com.crypted.explorer.gateway.model.resp.transaction

import com.crypted.explorer.gateway.model.vo.transaction.TransactionListVO
import java.io.Serializable

class TransactionListResp : Serializable {
    var totalPage: Int? = 0
    var totalTx: Int? = 0
    var transactionList: List<TransactionListVO?> = emptyList()
}