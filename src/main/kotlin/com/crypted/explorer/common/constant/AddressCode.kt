package com.crypted.explorer.common.constant

interface AddressCode {
    companion object {
        /**
         * not a externally owned address
         */
        const val NOT_EXTERNALLY_OWNED_ADDRESS = 1001

        /**
         * not a contract address
         */
        const val NOT_CONTRACT_ADDRESS = 1002

    }
}
