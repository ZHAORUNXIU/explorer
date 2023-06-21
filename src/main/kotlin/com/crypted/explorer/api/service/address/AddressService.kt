package com.crypted.explorer.api.service.address

import com.crypted.explorer.common.model.Result
import com.crypted.explorer.gateway.model.resp.address.AddressRankingResp

interface AddressService {
    fun getRankingByPage(pageNumber: Int, pageSize: Int): Result<AddressRankingResp?>

    //        val result: Result<List<??>> = addressService.getRankingByPage(paging)


}
