package com.crypted.explorer.gateway.model.resp.address

import com.crypted.explorer.gateway.model.vo.address.AddressRankingVO
import java.io.Serializable

class AddressRankingResp : Serializable {
    var totalPage: Int? = null
    var totalAddress: Int? = null
    var addressRanking: List<AddressRankingVO>? = null
}