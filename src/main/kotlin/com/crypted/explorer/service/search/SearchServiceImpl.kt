package com.crypted.explorer.service.search

import com.crypted.explorer.api.service.search.SearchService
import com.crypted.explorer.common.constant.SearchType
import org.springframework.stereotype.Service
import com.crypted.explorer.common.model.Result

@Service
class SearchServiceImpl : SearchService {

    private val BLOCK_NUMBER_PATTERN = "^[0-9]+$"

    private val ADDRESS_PATTERN = "^0x[0-9a-fA-F]{40}$"

    private val TX_HASH_PATTERN = "^0x[0-9a-fA-F]{64}$"

    override fun getSearchTypeByParam(param: String): Result<Int> {

        val blockNumberRegex = Regex(BLOCK_NUMBER_PATTERN)
        val addressRegex = Regex(ADDRESS_PATTERN)
        val txHashRegex = Regex(TX_HASH_PATTERN)

        return when {
            param.matches(blockNumberRegex) -> Result.success(SearchType.BLOCK.value)
            param.matches(addressRegex) -> Result.success(SearchType.ADDRESS.value)
            param.matches(txHashRegex) -> Result.success(SearchType.TRANSACTION.value)
            else -> Result.success(SearchType.UNKNOWN.value)
        }
    }
}
