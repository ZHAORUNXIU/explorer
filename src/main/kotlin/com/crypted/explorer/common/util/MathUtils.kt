package com.crypted.explorer.common.util

import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object MathUtils {

    fun ceilDiv(num: Int, divisor: Int): Int {
        return (num + divisor - 1) / divisor
    }

    fun convertWeiToEther(weiAmount: String): String {
        val wei = BigDecimal(weiAmount)
        val ether = wei.divide(BigDecimal("1000000000000000000")) // 1 Ether = 10^18 Wei
        return ether.toString()
    }

    /**
     * Asia/Singapore -> Asia/Seoul
     *
     * @param singaporeDateTime Singapore datetime
     * @return Seoul zoned dateTime
     */
    fun convertTimeZone(singaporeDateTime: Date): String {
        val singaporeZoneId = ZoneId.of("Asia/Singapore")
        val koreaZoneId = ZoneId.of("Asia/Seoul")

        val singaporeZonedDateTime = ZonedDateTime.ofInstant(singaporeDateTime.toInstant(), singaporeZoneId)
        val koreaZonedDateTime = singaporeZonedDateTime.withZoneSameInstant(koreaZoneId)

        return koreaZonedDateTime.format(DateTimeFormatter.ISO_INSTANT)
    }
}