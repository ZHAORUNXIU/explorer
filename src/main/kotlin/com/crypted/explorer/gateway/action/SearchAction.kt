package com.crypted.explorer.gateway.action

import com.crypted.explorer.api.service.search.SearchService
import com.crypted.explorer.common.constant.SearchType
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Log
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.http.MediaType
import javax.annotation.Resource
import javax.validation.constraints.NotNull

@RestController
@RequestMapping(value = ["/v1/search"])
@Validated
@Component
@Tag(name = "Search Service API", description = "API endpoints related to searching operations")
class SearchAction {

    companion object {
        private val LOG = LoggerFactory.getLogger(SearchAction::class.java)
    }

    @Resource
    private val searchService: SearchService? = null

    @GetMapping
    @Operation(summary = "Get searching type", description = "Get the searching type of provided param")
    @ApiResponse(responseCode = "200", description = "Success", content = [Content(
        mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = Schema(
            allowableValues = ["0", "1", "2", "3"]
        )
    )])
    @ApiResponse(responseCode = "500", description = "System Error")
    @ApiResponse(responseCode = "501", description = "Invalid Request")
    @ApiResponse(responseCode = "502", description = "Invalid Parameter")
    fun getSearchType(@Parameter(description = "param", required = true) @RequestParam(required = true) @NotNull param: String): Result<Int> {

        LOG.info(Log.format("success", Log.kv("api", "/search/")))

        val result: Result<Int> = searchService!!.getSearchTypeByParam(param)

        return Result.success(result.data)
    }

}
