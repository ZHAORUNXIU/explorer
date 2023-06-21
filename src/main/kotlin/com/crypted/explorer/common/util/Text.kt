package com.crypted.explorer.common.util

import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

object Text {
    /**
     * Trim the string and convert it to null if it becomes empty after trimming.
     */
    const val TRIM_TO_NULL = 1

    /**
     * Trim the string to an empty string.
     */
    const val TRIM_TO_BLANK = 2
    private val HEX_DIGITS = "0123456789ABCDEF".toCharArray()

    /**
     * The empty String
     */
    const val EMPTY = ""

    /**
     * Format a date (or time) object into a string with the specified pattern.
     *
     * @param date    The date (or time) object.
     * @param pattern The formatting pattern.
     * @return The formatted string.
     */
    fun formatTime(date: Date?, pattern: String?): String {
        return SimpleDateFormat(pattern).format(date)
    }

    /**
     * Split a string using a single character delimiter.
     *
     * @param value The string to split.
     * @param delim The delimiter character.
     * @return An array of strings.
     */
    fun splitArray(value: String, delim: Char): Array<String> {
        val res = split(value, delim)
        val ret = res.toTypedArray<String>()
        for (i in ret.indices) {
            ret[i] = trimEmpty(ret[i])
        }
        return ret
    }

    /**
     * Split a string using a delimiter character.
     *
     * @param value The source string.
     * @param delim The delimiter character.
     * @return A list of strings.
     */
    fun split(value: String, delim: Char): List<String> {
        val end = value.length
        val res: MutableList<String> = ArrayList()
        var start = 0
        for (i in 0 until end) {
            if (value[i] == delim) {
                if (start == i) {
                    res.add("")
                } else {
                    res.add(value.substring(start, i))
                }
                start = i + 1
            }
        }
        if (start == 0) {
            res.add(value)
        } else if (start != end) {
            res.add(value.substring(start, end))
        } else {
            for (i in res.indices.reversed()) {
                if (res[i].isEmpty()) {
                    res.removeAt(i)
                } else {
                    break
                }
            }
        }
        return res
    }

    /**
     * Get the MD5 hash of a string and convert it to a hexadecimal string.
     *
     * @param src  The source string.
     * @param salt The salt string.
     * @return The MD5 hash as a hexadecimal string.
     */
    fun md5(src: String, salt: String?): String {
        return toHexString(digest("MD5", src, salt))
    }

    /**
     * Get the SHA-256 hash of a string and convert it to a hexadecimal string.
     *
     * @param src  The source string.
     * @param salt The salt string.
     * @return The SHA-256 hash as a hexadecimal string.
     */
    fun sha256(src: String, salt: String?): String {
        return toHexString(digest("SHA-256", src, salt))
    }

    /**
     * Merge two byte arrays.
     *
     * @param x The first byte array.
     * @param y The second byte array.
     * @return The merged byte array.
     */
    fun merge(x: ByteArray, y: ByteArray): ByteArray {
        val b = ByteArray(x.size + y.size)
        System.arraycopy(x, 0, b, 0, x.size)
        System.arraycopy(y, 0, b, x.size, y.size)
        return b
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param b Byte array
     * @return Hexadecimal string
     */
    fun toHexString(b: ByteArray?): String {
        val buf = StringBuilder(b!!.size * 2)
        for (n in b) {
            val v = if (n < 0) n + 256 else n.toInt()
            buf.append(HEX_DIGITS[v shr 4]).append(HEX_DIGITS[v % 16])
        }
        return buf.toString()
    }

    fun isBlank(cs: CharSequence?): Boolean {
        var strLen: Int = 0
        if (cs == null || cs.length.also { strLen = it } == 0) {
            return true
        }
        for (i in 0 until strLen) {
            if (!Character.isWhitespace(cs[i])) {
                return false
            }
        }
        return true
    }

    fun isBlank(cs: CharSequence?, end: Int): Boolean {
        var strLen: Int = 0
        if (cs == null || cs.length.also { strLen = it } == 0) {
            return true
        }
        if (end > 0 && end < strLen) {
            strLen = end
        }
        for (i in 0 until strLen) {
            if (!Character.isWhitespace(cs[i])) {
                return false
            }
        }
        return true
    }

    fun isNotBlank(cs: CharSequence?): Boolean {
        return !isBlank(cs)
    }

    fun isEmpty(cs: CharSequence?): Boolean {
        return cs == null || cs.length == 0
    }

    fun startsWith(s: String, c: Char): Boolean {
        return s.length != 0 && s[0] == c
    }

    fun endsWith(s: String, c: Char): Boolean {
        return s.length != 0 && s[s.length - 1] == c
    }

    fun trim(cs: String?): String? {
        return cs?.trim { it <= ' ' }
    }

    fun trimNull(cs: String?): String? {
        val s = trim(cs)
        return if (isEmpty(s)) null else s
    }

    fun trimEmpty(cs: String?): String {
        val s = trim(cs)
        return s ?: ""
    }

    fun trimToFlag(src: String, flag: String?): String {
        val index = src.indexOf(flag!!)
        return if (index == -1) {
            src
        } else src.substring(0, index)
    }

    /**
     * Returns true if the string is null or has a length equal to the specified length.
     *
     * @param s      字符串
     * @param length 长度
     */
    fun validLength(s: String?, length: Int): Boolean {
        return if (s == null) {
            true
        } else s.length == length
    }

    /**
     * Returns true if the string is null or has a length within the specified range (inclusive).
     *
     * @param s   String
     * @param min Minimum length
     * @param max Maximum length
     */
    fun validLength(s: String?, min: Int, max: Int): Boolean {
        return if (s == null) {
            true
        } else s.length >= min && s.length <= max
    }

    fun uuid(): String {
        return UUID.randomUUID().toString().replace("-".toRegex(), "")
    }

    private fun digest(algorithm: String, src: String, salt: String?): ByteArray? {
        var a = src.toByteArray()
        return try {
            if (salt != null && salt.length > 0) {
                a = merge(a, salt.toByteArray())
            }
            MessageDigest.getInstance(algorithm).digest(a)
        } catch (e: Exception) {
            null
        }
    }
}
