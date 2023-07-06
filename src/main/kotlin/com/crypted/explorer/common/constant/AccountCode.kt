package com.crypted.explorer.common.constant

enum class AccountCode(val code: Int, val message: String) {

    NOT_EXTERNALLY_OWNED_ACCOUNT(1001, "The account is not a EOA."),

    NOT_CONTRACT_ACCOUNT(1002, "The account is not a CA.")
}