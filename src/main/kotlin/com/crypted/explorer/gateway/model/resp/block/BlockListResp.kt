package com.crypted.explorer.gateway.model.resp.block

import com.crypted.explorer.gateway.model.vo.block.BlockListVO
import java.io.Serializable

class BlockListResp : Serializable {
    var totalPage: Int? = null
    var totalBlock: Int? = null
    var blockList: List<BlockListVO>? = null
}