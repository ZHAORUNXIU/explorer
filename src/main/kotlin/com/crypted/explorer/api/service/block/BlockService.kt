package com.crypted.explorer.api.service.block

import com.crypted.explorer.common.model.Result
import com.crypted.explorer.gateway.model.resp.address.AddressRankingResp
import com.crypted.explorer.gateway.model.resp.block.BlockListResp

interface BlockService {
    fun getListByPage(pageNumber: Int, pageSize: Int): Result<BlockListResp?>
}
