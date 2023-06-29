package com.crypted.explorer.api.service.block

import com.crypted.explorer.common.model.Result
import com.crypted.explorer.gateway.model.resp.block.BlockInfoResp
import com.crypted.explorer.gateway.model.resp.block.BlockListResp

interface BlockService {
    fun getListByPage(pageNumber: Int, pageSize: Int): Result<BlockListResp?>

    fun getInfoByBlockNumber(blockNumber: Int): Result<BlockInfoResp?>

}
