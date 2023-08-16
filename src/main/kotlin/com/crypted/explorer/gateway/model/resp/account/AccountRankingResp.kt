package com.crypted.explorer.gateway.model.resp.account

import com.crypted.explorer.api.model.vo.account.AccountRankingVO
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class AccountRankingResp : Serializable {
    @Schema(description = "totalPage", type = "integer", example = "2")
    var totalPage: Int? = 0
    @Schema(description = "totalAccount", type = "integer")
    var totalAccount: Int? = 0
    @Schema(description = "accountRanking", type = "array")
    var accountRanking: List<AccountRankingVO>? = null
}