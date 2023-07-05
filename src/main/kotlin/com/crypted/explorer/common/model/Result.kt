package com.crypted.explorer.common.model

import com.crypted.explorer.common.constant.Code
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

data class Result<T>(
        val code: Int,
        val data: T?,
        var message: String?
) : Serializable {
    companion object {
        fun <E> failure(code: Int): Result<E> {
            return Result(code, null, null)
        }

        fun <E> failure(code: Int, data: E): Result<E> {
            return Result(code, data, null)
        }

        fun <E> failure(code: Int, data: E, message: String): Result<E> {
            return Result(code, data, message)
        }

        fun <E> failure(code: Int, message: String): Result<E> {
            return Result(code, null, message,)
        }

        @Suppress("unchecked_cast")
        fun <E> failure(src: Result<*>): Result<E> {
            return src as Result<E>
        }

        fun <E> success(data: E?): Result<E> {
            return Result(Code.SUCCESS, data, null)
        }
    }

    constructor() : this(Code.SYSTEM_ERROR, null, null)

    fun isSuccess(): Boolean {
        return code == Code.SUCCESS
    }

}
