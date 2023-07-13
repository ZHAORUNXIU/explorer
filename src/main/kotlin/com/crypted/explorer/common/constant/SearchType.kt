package com.crypted.explorer.common.constant

enum class SearchType(val value: Int) {

    UNKNOWN(0),

    CONTRACT_ACCOUNT(1),

    EXTERNALLY_OWNED_ACCOUNT(2),

    BLOCK(3),

    TRANSACTION(4),

    TOKEN(5)

}