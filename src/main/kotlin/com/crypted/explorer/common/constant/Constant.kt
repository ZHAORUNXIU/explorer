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
         * default time zone
         */
        val DEFAULT_TIME_ZONE = TimeZone.getTimeZone("GMT+9:00")

        /**
         * user tokenKey
         */
        const val LOGIN_TOKEN = "EXPLORER-TOKEN"

        /**
         * regex zero start
         */
        const val REGEX_ZERO_START = "^0+"

        /**
         * default code
         */
        const val DEFAULT_CODE = "123456"
        const val EXPIRED_MAX_TIME_STAMP = 2840112000000L
        const val REGEX_SPACE = "\\s"
    }
}
