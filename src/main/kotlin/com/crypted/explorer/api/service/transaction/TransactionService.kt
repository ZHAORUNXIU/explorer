package com.crypted.explorer.api.service.transaction

import com.crypted.explorer.api.model.domain.token.TokenDO
import com.crypted.explorer.common.constant.TokenType
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Text
import com.crypted.explorer.gateway.model.resp.transaction.TokenTransferListResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionInfoResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionListResp
import com.crypted.explorer.gateway.model.resp.transaction.TokenTransferredVO
import com.crypted.explorer.api.model.vo.transaction.TransactionHistoryVO

interface TransactionService {
    fun getListByPage(fromAddress: String?, toAddress: String?, blockNumber: Long?, status: Int?, pageNumber: Int, pageSize: Int): Result<TransactionListResp?>

    fun getInfoByTxHash(txHash: String): Result<TransactionInfoResp?>

    fun getTokenTransferredByTxHash(txHash: String): Result<TokenTransferredVO?>

    fun getTransactionAmountByBlockNumber(blockNumber: Long): Result<Int>

    fun getHistory(): Result<List<TransactionHistoryVO>?>

    fun getTransferListByTokenAddress(tokenAddress: String, pageNumber: Int, pageSize: Int): Result<TokenTransferListResp?>

    fun getTotalTransfers(tokenType: String, tokenAddress: String): Result<Int>

}
