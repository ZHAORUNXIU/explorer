package com.crypted.explorer.gateway.model.resp.block

import java.io.Serializable

class BlockInfoResp : Serializable {
    var blockNumber: Int? = null
    var timestamp: String? = null
    var txCount: Int? = null
    var blockReward: String? = null
    var symbol: String? = null
    var latestBlock: Int? = null
}