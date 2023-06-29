package com.crypted.explorer.gateway.action

import com.crypted.explorer.api.service.address.AddressService
import com.crypted.explorer.api.service.token.TokenService
import com.crypted.explorer.common.constant.AddressCode
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.Log
import com.crypted.explorer.gateway.model.resp.token.TokenInfoResp
import com.crypted.explorer.gateway.model.resp.token.TokenListResp
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@RestController
@RequestMapping(value = ["/v1/token"])
@Validated
@Component
class TokenAction {

    companion object {
        private val LOG = LoggerFactory.getLogger(TokenAction::class.java)
    }

    @Resource
    private val tokeService: TokenService? = null

    @Resource
    private val addressService: AddressService? = null

    @GetMapping("/list")
    fun getList(@RequestParam(required = true) @NotNull @Min(0) pageNumber: Int,
                      @RequestParam(required = true) @NotNull @Min(0) pageSize: Int): Result<TokenListResp?> {

        LOG.info(Log.format("success", Log.kv("api", "/token/list")))

        val result: Result<TokenListResp?> = tokeService!!.getListByPage(pageNumber, pageSize)

        return Result.success(result.data)
    }

    @GetMapping("/{contractAddress}")
    fun getInfoByContractAddress(@PathVariable("contractAddress") @NotNull contractAddress: String): Result<TokenInfoResp?> {

        LOG.info(Log.format("success", Log.kv("api", "token/")))

        // Contract Address
        if (addressService!!.checkAddressIsContract(contractAddress).data == false)
            return Result.failure(AddressCode.NOT_CONTRACT_ADDRESS)

        val result: Result<TokenInfoResp?> = tokeService!!.getInfoByContractAddress(contractAddress)

        return Result.success(result.data)
    }

}
