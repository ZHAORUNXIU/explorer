package com.crypted.explorer.gateway.action

import com.crypted.explorer.api.service.search.SearchService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Log
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@RestController
@RequestMapping(value = ["/v1/search"])
@Validated
@Component
class SearchAction {

    companion object {
        private val LOG = LoggerFactory.getLogger(SearchAction::class.java)
    }

    @Resource
    private val searchService: SearchService? = null

    @GetMapping
    fun getSearchType(@RequestParam(required = true) @NotNull param: String): Result<Int> {

        LOG.info(Log.format("success", Log.kv("api", "/search/")))

        val result: Result<Int> = searchService!!.getSearchTypeByParam(param)

        return Result.success(result.data)
    }

}
