package com.crypted.explorer.api.service.token

import com.crypted.explorer.api.model.domain.token.TokenDO
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.gateway.model.resp.token.TokenInfoResp
import com.crypted.explorer.gateway.model.resp.token.TokenListResp

interface TokenService {

    fun getTokenListByAddress(address: String): Result<List<TokenDO>?>

    fun getListByPage(pageNumber: Int, pageSize: Int): Result<TokenListResp?>

    fun getInfoByContractAddress(address: String): Result<TokenInfoResp?>

    fun getTokenName(tokenDO: TokenDO): Result<String?>

    fun getTokenBalance(tokenDO: TokenDO): Result<String?>

}
