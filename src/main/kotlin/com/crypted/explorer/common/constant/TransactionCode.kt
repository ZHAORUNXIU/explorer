package com.crypted.explorer.common.constant

enum class TransactionCode(val code: Int, val message: String) {

    NOT_TOKEN_TRANSFER(3001, "The transaction is not a token transfer."),
}