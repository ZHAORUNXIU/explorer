package com.crypted.explorer.api.service.search

import com.crypted.explorer.common.model.Result

interface SearchService {
    fun getSearchTypeByParam(param: String): Result<Int>
}
