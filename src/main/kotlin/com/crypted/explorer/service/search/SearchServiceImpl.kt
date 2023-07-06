package com.crypted.explorer.service.search

import com.crypted.explorer.api.service.search.SearchService
import com.crypted.explorer.common.constant.SearchType
import org.springframework.stereotype.Service
import com.crypted.explorer.common.model.Result

@Service
class SearchServiceImpl : SearchService {

    private val BLOCK_NUMBER_PATTERN = "^[0-9]+$"

    private val ACCOUNT_ADDRESS_PATTERN = "^0x[0-9a-fA-F]{40}$"

    private val TX_HASH_PATTERN = "^0x[0-9a-fA-F]{64}$"

    override fun getSearchTypeByParam(param: String): Result<String> {

        val blockNumberRegex = Regex(BLOCK_NUMBER_PATTERN)
        val accountAddressRegex = Regex(ACCOUNT_ADDRESS_PATTERN)
        val txHashRegex = Regex(TX_HASH_PATTERN)

        return when {
            param.matches(blockNumberRegex) -> Result.success(SearchType.BLOCK.value)
            param.matches(accountAddressRegex) -> Result.success(SearchType.ACCOUNT.value)
            param.matches(txHashRegex) -> Result.success(SearchType.TRANSACTION.value)
            else -> Result.success(SearchType.UNKNOWN.value)
        }
    }
}
