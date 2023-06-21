package com.crypted.explorer.gateway.action

import com.crypted.explorer.api.service.address.AddressService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Log
import com.crypted.explorer.gateway.model.resp.address.AddressInfoResp
import com.crypted.explorer.gateway.model.resp.address.AddressRankingResp
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@RestController
@RequestMapping(value = ["/v1/address"])
@Validated
@Component
class AddressAction {

    companion object {
        private val LOG = LoggerFactory.getLogger(AddressAction::class.java)
    }

    @Resource
    private val addressService: AddressService? = null

    @GetMapping("/ranking")
    fun getRanking(@RequestParam(required = true) @NotNull @Min(0) pageNumber: Int,
             @RequestParam(required = true) @NotNull @Min(0) pageSize: Int): Result<AddressRankingResp?> {

        LOG.info(Log.format("success", Log.kv("api", "address/ranking")))

        var result: Result<AddressRankingResp?> = addressService!!.getRankingByPage(pageNumber, pageSize)

        return Result.success(result.data)
    }

    @GetMapping("/info")
    fun getInfoByNumber(@RequestParam(required = true) @NotNull @Min(0) address: String): Result<AddressInfoResp?> {
        LOG.info(Log.format("success", Log.kv("api", "address/info")))
        return Result.success(null)
    }

}
