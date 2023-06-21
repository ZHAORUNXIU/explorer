package com.crypted.explorer.gateway.model.resp.transaction

import com.crypted.explorer.gateway.model.vo.transaction.TransactionHistoryVO
import java.io.Serializable

class TransactionHistoryResp : Serializable {
    var transactionList: List<TransactionHistoryVO>? = null
}