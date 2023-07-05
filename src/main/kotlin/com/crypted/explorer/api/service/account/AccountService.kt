package com.crypted.explorer.api.service.account

import com.crypted.explorer.common.model.Result
import com.crypted.explorer.gateway.model.resp.account.AccountInfoResp
import com.crypted.explorer.gateway.model.resp.account.AccountRankingResp

interface AccountService {
    fun getRankingByPage(pageNumber: Int, pageSize: Int): Result<AccountRankingResp?>

    fun getInfoByAddress(address: String): Result<AccountInfoResp?>

    fun checkAccountIsContract(address: String): Result<Boolean>

}
