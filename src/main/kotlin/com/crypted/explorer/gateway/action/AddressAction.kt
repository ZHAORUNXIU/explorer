package com.crypted.explorer.gateway.action

import com.crypted.explorer.api.service.address.AddressService
import com.crypted.explorer.common.constant.AddressCode
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Log
import com.crypted.explorer.gateway.model.resp.address.AddressInfoResp
import com.crypted.explorer.gateway.model.resp.address.AddressRankingResp
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
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

        val result: Result<AddressRankingResp?> = addressService!!.getRankingByPage(pageNumber, pageSize)

        return Result.success(result.data)
    }

    /**
     * EOA
     */
    @GetMapping("/{address}")
    fun getInfoByAddress(@PathVariable("address") @NotNull address: String): Result<AddressInfoResp?> {

        LOG.info(Log.format("success", Log.kv("api", "address/")))

        // EOA
        if (addressService!!.checkAddressIsContract(address).data == true)
            return Result.failure(AddressCode.NOT_EXTERNALLY_OWNED_ADDRESS)

        val result: Result<AddressInfoResp?> = addressService.getInfoByAddress(address)

        return Result.success(result.data)
    }

//    @GetMapping("/transactions/{address}")
//    fun getTransactionsByNumber(@PathVariable("address") @NotNull address: String,
//                                @RequestParam(required = true) @NotNull @Min(0) pageNumber: Int,
//                                @RequestParam(required = true) @NotNull @Min(0) pageSize: Int): Result<AddressTransactionListResp?> {
//
//        LOG.info(Log.format("success", Log.kv("api", "address/transactions")))
//
//        val result: Result<AddressTransactionListResp?> = addressService!!.getTransactionsByAddress(address, pageNumber, pageSize)
//
//
//        return Result.success(result.data)
//    }
}
