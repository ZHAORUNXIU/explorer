package com.crypted.explorer.api.service.token

import com.crypted.explorer.common.model.Result
import com.crypted.explorer.gateway.model.resp.token.TokenInfoResp
import com.crypted.explorer.gateway.model.resp.token.TokenListResp
import com.crypted.explorer.gateway.model.resp.token.TokenTransferListResp
import com.crypted.explorer.gateway.model.vo.token.TokenTransferVO
import com.crypted.explorer.gateway.model.vo.token.TokenVO

interface TokenService {

    fun getTokenHoldingsByHolder(holder: String): Result<List<TokenVO>?>

    fun getListByPage(pageNumber: Int, pageSize: Int): Result<TokenListResp?>

    fun getInfoByContractAddress(address: String): Result<TokenInfoResp?>

    fun getTransferListByPage(tokenAddress: String, pageNumber: Int, pageSize: Int): Result<TokenTransferListResp?>

    fun getTokenInfoByTxHash(txHash: String): Result<TokenTransferVO?>

}
