package com.crypted.explorer.gateway.model.resp.search

import com.crypted.explorer.gateway.model.vo.token.TokenVO
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class SearchTypeResp : Serializable {
    @Schema(description = "searchType", type = "string")
    var searchType: String? = null
}