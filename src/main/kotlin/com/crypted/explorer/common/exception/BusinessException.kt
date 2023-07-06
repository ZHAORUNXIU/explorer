package com.crypted.explorer.common.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Business Error (Transaction and Business Logic Handling)
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class BusinessException : RuntimeException {
    @JvmField
    var code = 0
    override var message: String? = null

    constructor() : super()
    constructor(code: Int) : super("") {
        this.code = code
        message = ""
    }

    constructor(code: Int, message: String?) : super(message) {
        this.code = code
        this.message = message
    }

    constructor(code: Int, message: String?, cause: Throwable?) : super(message, cause) {
        this.code = code
        this.message = message
    }

    constructor(cause: Throwable?) : super(cause)
    protected constructor(
        message: String?,
        cause: Throwable?,
        enableSuppression: Boolean,
        writableStackTrace: Boolean
    ) : super(message, cause, enableSuppression, writableStackTrace)
}
