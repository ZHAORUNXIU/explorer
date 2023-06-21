package com.crypted.explorer.common.constant

interface Code {
    companion object {
        /**
         * Success
         */
        const val SUCCESS = 200

        /**
         * Forbidden
         */
        const val FORBIDDEN = 403

        /**
         * Resource Not Found
         */
        const val NOT_FOUND = 404

        /**
         * System Error
         */
        const val SYSTEM_ERROR = 500

        /**
         * Invalid Request
         */
        const val ILLEGAL_REQUEST = 501

        /**
         * Invalid Parameter
         */
        const val ILLEGAL_PARAM = 502

        /**
         * Request Exceeds Size Limit
         */
        const val EXCEED_SIZE = 503

        /**
         * Missing Parameter
         */
        const val PARAM_MISSING = 504

        /**
         * Frequent Invocation
         */
        const val FREQUENT_INVOKE = 510

        /**
         * Blacklisted User
         */
        const val BLACKLIST = 511

        /**
         * Anonymous Access Rejected
         */
        const val REJECT_ANONYMOUS = 512
    }
}
