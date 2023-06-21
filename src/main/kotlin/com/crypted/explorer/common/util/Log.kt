package com.crypted.explorer.common.util

import com.crypted.explorer.common.model.KeyValue
import com.crypted.explorer.common.model.Result

class Log {
    var accessId: String? = null
    var ip: String? = null
    private val params: MutableMap<String, String> = HashMap()
    fun getParams(): Map<String, String> {
        return params
    }

    fun setParams(params: Map<String, String>?) {
        if (params != null && !params.isEmpty()) {
            this.params.putAll(params)
        }
    }

    fun setParam(key: String, value: String) {
        if (Text.isNotBlank(key) && Text.isNotBlank(value)) {
            params[key] = value
        }
    }

    fun removeParam(key: String): String? {
        return params.remove(key)
    }

    fun getParam(key: String): String? {
        return params[key]
    }

    companion object {
        private val local = ThreadLocal.withInitial { Log() }
        fun format(code: Int, message: String?, vararg kvs: KeyValue?): String {
            val buf = StringBuilder(200)
            if (code != 0) {
                buf.append("code:").append(code).append('\t')
            }
            if (Text.isNotBlank(message)) {
                buf.append("msg:").append(message).append('\t')
            }
            if (kvs != null) {
                for (kv in kvs) {
                    buf.append(kv?.key).append(':').append(kv?.getValue()).append('\t')
                }
            }
            val log = local.get()
            if (Text.isNotBlank(log.accessId)) {
                buf.append("uuid:").append(log.accessId).append('\t')
            }
            if (Text.isNotBlank(log.ip)) {
                buf.append("ip:").append(log.ip).append('\t')
            }
            val localParams = log.getParams()
            if (!localParams.isEmpty()) {
                localParams.forEach { (k: String?, v: String?) ->
                    if (Text.isNotBlank(k) && Text.isNotBlank(v)) {
                        buf.append(k).append(':').append(v).append('\t')
                    }
                }
            }
            val len = buf.length
            if (len > 0) {
                val ch = buf[len - 1]
                if (ch == ' ' || ch == '\t') {
                    buf.deleteCharAt(len - 1)
                }
            }
            return if (buf.length == 0) "" else buf.toString()
        }

        fun format(message: String?, vararg kvs: KeyValue?): String {
            return format(0, message, *kvs)
        }

        fun <T> format(result: Result<T>, vararg kvs: KeyValue?): String {
            return format(result.code, result.message, *kvs)
        }

        fun kv(key: String, value: Any): KeyValue {
            return KeyValue(key, value)
        }

        fun get(): Log {
            return local.get()
        }

        fun remove() {
            local.remove()
        }
    }
}
