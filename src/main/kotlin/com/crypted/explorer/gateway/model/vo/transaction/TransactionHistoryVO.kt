package com.crypted.explorer.gateway.model.vo.transaction

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

class TransactionHistoryVO : Serializable {
    @Schema(description = "date", type = "string", format = "date-time", example = "011-12-03T10:15:30Z")
    var date: String? = null
//    var price: String? = null
    @Schema(description = "count", type = "integer")
    var count: Int? = 0
}