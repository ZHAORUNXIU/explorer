package com.crypted.explorer.gateway.model.resp.block

import com.crypted.explorer.gateway.model.vo.block.BlockListVO
import java.io.Serializable

class BlockListResp : Serializable {
    var totalPage: Int? = 0
    var totalBlock: Int? = 0
    var blockList: List<BlockListVO?> = emptyList()
}