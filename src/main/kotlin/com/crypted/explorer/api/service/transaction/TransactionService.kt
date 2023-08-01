package com.crypted.explorer.api.service.transaction

import com.crypted.explorer.common.model.Result
import com.crypted.explorer.gateway.model.resp.transaction.TransactionInfoResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionListResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionTokenInfoResp
import com.crypted.explorer.gateway.model.vo.transaction.TransactionHistoryVO

interface TransactionService {
    fun getListByPage(fromAddress: String?, toAddress: String?, blockNumber: Long?, status: Int?, pageNumber: Int, pageSize: Int): Result<TransactionListResp?>

    fun getInfoByTxHash(txHash: String): Result<TransactionInfoResp?>

    fun getTokenInfoByTxHash(txHash: String): Result<TransactionTokenInfoResp?>

    fun getTransactionAmountByBlockNumber(blockNumber: Long): Result<Int>

    fun getHistory(): Result<List<TransactionHistoryVO>?>

}
