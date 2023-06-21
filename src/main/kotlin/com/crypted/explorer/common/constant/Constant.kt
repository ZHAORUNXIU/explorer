package com.crypted.explorer.common.constant

import java.util.*

/**
 *
 * @author Raine.Jo
 * @date 2023-06-20 11:00:00
 */
interface Constant {
    companion object {
        const val DOT = "."
        const val COMMA = ","
        const val asterisk = "*"
        const val UNDERLINE = "_"
        const val EQUAL_SIGN = "="
        const val SEMICOLON = ";"
        const val PERCENT_SIGN = "%"
        const val ID = "id"
        const val USER_ID = "userId"

        /**
         * 默认北京时间东八区
         */
        val DEFAULT_TIME_ZONE = TimeZone.getTimeZone("GMT+8:00")

        /**
         * 用户tokenKey
         */
        const val LOGIN_TOKEN = "RESUME-TOKEN"

        /**
         * 0开头正则
         */
        const val REGEX_ZERO_START = "^0+"

        /**
         * 默认的验证码
         */
        const val DEFAULT_CODE = "123456"
        const val EXPIRED_MAX_TIME_STAMP = 2840112000000L
        const val REGEX_SPACE = "\\s"
    }
}
