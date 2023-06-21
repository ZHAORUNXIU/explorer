package com.crypted.explorer.common.model

class KeyValue(var key: String, private var value: Any) {
    fun getValue(): String {
        return value.toString()
    }
}
