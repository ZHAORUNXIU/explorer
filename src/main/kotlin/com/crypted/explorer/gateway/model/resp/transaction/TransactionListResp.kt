package com.crypted.explorer.gateway.model.resp.transaction

import com.crypted.explorer.api.model.vo.transaction.TransactionListVO
import io.swagger.v3.oas.annotations.media.Schema

import java.io.Serializable

class TransactionListResp : Serializable {
    @Schema(description = "totalPage", type = "integer")
    var totalPage: Int? = 0
    @Schema(description = "totalTx", type = "integer")
    var totalTx: Int? = 0
    @Schema(description = "transactionList", type = "array")
    var transactionList: List<TransactionListVO?> = emptyList()
}