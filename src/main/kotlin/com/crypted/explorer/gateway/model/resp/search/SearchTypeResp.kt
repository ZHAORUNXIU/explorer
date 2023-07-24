package com.crypted.explorer.gateway.model.resp.search

import com.crypted.explorer.common.constant.SearchType
import com.crypted.explorer.gateway.model.vo.token.TokenVO
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class SearchTypeResp : Serializable {
    @Schema(description = "searchType 0-UNKNOWN 1-CA 2-EOA 3-BLOCK 4-TRANSACTION 5-TOKEN", type = "integer")
    var searchType: Int? = null
}