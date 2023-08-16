package com.crypted.explorer.api.model.vo.block

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class BlockInfoVO : Serializable {
    var blockNumber: Long? = 0L
    var timestamp: String? = null
    var blockReward: String? = null
    var symbol: String? = null
    var latestBlock: Long? = 0L
}