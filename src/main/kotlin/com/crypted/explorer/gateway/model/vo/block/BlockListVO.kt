package com.crypted.explorer.gateway.model.vo.block

import java.io.Serializable

class BlockListVO : Serializable {
    var blockNumber: Int? = null
    var timestamp: String? = null
    var txCount: Int? = null
    var blockReward: String? = null
    var symbol: String? = null
}