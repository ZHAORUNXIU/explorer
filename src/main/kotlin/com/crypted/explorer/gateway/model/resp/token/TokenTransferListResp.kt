package com.crypted.explorer.gateway.model.resp.token

import com.crypted.explorer.gateway.model.vo.token.TokenTransferListVO
import io.swagger.v3.oas.annotations.media.Schema

import java.io.Serializable

class TokenTransferListResp : Serializable {
    @Schema(description = "totalPage", type = "integer")
    var totalPage: Int? = 0
    @Schema(description = "totalTx", type = "integer")
    var totalTx: Int? = 0
    @Schema(description = "tokenTransferList", type = "array")
    var tokenTransferList: List<TokenTransferListVO?> = emptyList()
}