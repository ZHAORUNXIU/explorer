package com.crypted.explorer.common.constant

interface AccountCode {
    companion object {
        /**
         * not a externally owned account
         */
        const val NOT_EXTERNALLY_OWNED_ACCOUNT = 1001

        /**
         * not a contract account
         */
        const val NOT_CONTRACT_ACCOUNT = 1002

    }
}
