package com.crypted.explorer.api.model.vo.account

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

open class AccountVO : Serializable {

    @Schema(description = "accountAddress", type = "String")
    var address: String? = null
    @Schema(description = "accountIsContract", type = "boolean", example = "true")
    var isContract: Boolean? = null
    @Schema(description = "accountNameTag", type = "String")
    var nameTag: String? = null
}