package com.crypted.explorer.gateway.model.resp.block

import java.io.Serializable

class BlockInfoResp : Serializable {
    var blockNumber: Int? = 0
    var timestamp: String? = null
    var txCount: Int? = 0
    var blockReward: String? = null
    var symbol: String? = null
    var latestBlock: Int? = 0
}