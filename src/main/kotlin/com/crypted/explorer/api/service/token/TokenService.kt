package com.crypted.explorer.api.service.token

import com.crypted.explorer.common.model.Result
import com.crypted.explorer.gateway.model.resp.token.TokenListResp
import com.crypted.explorer.api.model.vo.account.TokenHoldingVO
import com.crypted.explorer.api.model.vo.token.TokenInfoVO
import com.crypted.explorer.api.model.vo.token.TokenVO

interface TokenService {

    fun getTokenHoldingsByHolder(holder: String): Result<List<TokenHoldingVO>?>

    fun getListByPage(pageNumber: Int, pageSize: Int): Result<TokenListResp?>

    fun getInfoByContractAddress(address: String): Result<TokenInfoVO>

//    fun getTransferListByPage(tokenAddress: String, pageNumber: Int, pageSize: Int): Result<TokenTransferListResp?>

//    fun getTokenTransferByTxHash(txHash: String): Result<TokenTransferVO?>

    fun getByAddress(address: String): Result<TokenVO>

}
