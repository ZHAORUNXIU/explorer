package com.crypted.explorer.api.service.transaction

import com.crypted.explorer.common.model.Result
import com.crypted.explorer.gateway.model.resp.block.BlockListResp
import com.crypted.explorer.gateway.model.resp.transaction.TransactionListResp

interface TransactionService {
    fun getListByPage(pageNumber: Int, pageSize: Int): Result<TransactionListResp?>
}
