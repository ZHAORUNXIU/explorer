package com.crypted.explorer.gateway.model.vo.block

import java.io.Serializable

class BlockListVO : Serializable {
    var blockNumber: Int? = 0
    var timestamp: String? = null
    var txCount: Int? = 0
    var blockReward: String? = null
    var symbol: String? = null
}