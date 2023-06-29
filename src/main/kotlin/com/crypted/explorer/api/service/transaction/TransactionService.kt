package com.crypted.explorer.api.service.transaction

import com.crypted.explorer.common.model.Result
import com.crypted.explorer.gateway.model.resp.transaction.TransactionInfoResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionListResp
import com.crypted.explorer.gateway.model.vo.transaction.TransactionHistoryVO

interface TransactionService {
    fun getListByPage(fromAddress: String?, toAddress: String?, blockNumber: Int?, pageNumber: Int, pageSize: Int): Result<TransactionListResp?>

    fun getInfoByTxHash(txHash: String): Result<TransactionInfoResp?>

    fun getTransactionAmount(): Result<Int>

    fun getHistory(): Result<List<TransactionHistoryVO>?>

}
