package com.crypted.explorer.service.address

import com.crypted.explorer.api.model.domain.address.AddressDO
import com.crypted.explorer.api.model.domain.token.TokenDO
import com.crypted.explorer.api.service.address.AddressService
import com.crypted.explorer.api.service.token.TokenService
import com.crypted.explorer.api.service.transaction.TransactionService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.MathUtils
import com.crypted.explorer.gateway.model.resp.address.AddressInfoResp
import com.crypted.explorer.gateway.model.resp.address.AddressRankingResp
import com.crypted.explorer.gateway.model.vo.address.AddressRankingVO
import com.crypted.explorer.gateway.model.vo.address.TokenVO
import com.crypted.explorer.service.token.ContractRepository
import com.crypted.explorer.service.token.DeployedContractRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import javax.annotation.Resource
import kotlin.streams.toList

@Service
class AddressServiceImpl : AddressService {

    private val RANKING_BY_BALANCE = "balance"

    @Value("\${address.symbol}")
    private lateinit var symbol: String

    @Resource
    private val addressRepository: AddressRepository? = null

    @Resource
    private val tokenService: TokenService? = null

    @Resource
    private val transactionService: TransactionService? = null

    override fun getRankingByPage(pageNumber: Int, pageSize: Int): Result<AddressRankingResp?> {

        val pageable: Pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.DESC, RANKING_BY_BALANCE)
        val addressDOList: List<AddressDO?> = addressRepository!!.findAll(pageable).content
        val addressRanking: List<AddressRankingVO> = addressDOList.stream().map { addressDO ->
            val addressRankingVO = AddressRankingVO()
            addressRankingVO.address = addressDO?.address
            addressRankingVO.isContract = addressDO?.isContract == 0
            addressRankingVO.balance = addressDO?.balance
            addressRankingVO.symbol = this@AddressServiceImpl.symbol
//            addressRankingVO.percentage = addressDO?.
            // txCount equals addressDO.nonce
//            addressRankingVO.txCount = addressDO?.address?.let { transactionService!!.getTransactionAmountByAddress(it).data }
            addressRankingVO.txCount = addressDO?.nonce
            addressRankingVO
        }.toList()


        val totalAddress: Int = addressRepository.count().toInt()

        val qddressRankingResp = AddressRankingResp().apply {
            this.totalPage = MathUtils.ceilDiv(totalAddress, pageSize)
            this.totalAddress = totalAddress
            this.addressRanking = addressRanking
        }

        return Result.success(qddressRankingResp)
    }

    override fun getInfoByAddress(address: String): Result<AddressInfoResp?> {

        val addressDO: AddressDO? = addressRepository!!.findByAddress(address)

        val tokenDOList: List<TokenDO>? = tokenService!!.getTokenListByAddress(address).data

        val tokenHoldings: List<TokenVO?> = tokenDOList?.stream()?.map { tokenDO ->
            val tokenVO = TokenVO()
            tokenVO.name = tokenService.getTokenName(tokenDO).data
            tokenVO.tokenSymbol = tokenDO?.symbol
            // from ercXX_hold table
            tokenVO.tokenBalance = tokenService.getTokenBalance(tokenDO).data
            tokenVO.imageUrl = tokenDO?.image
            tokenVO
        }?.toList() ?: emptyList()

        val addressInfoResp = AddressInfoResp().apply {
            this.address = addressDO?.address
            this.balance = addressDO?.balance
            this.symbol = this@AddressServiceImpl.symbol
            this.tokenCount = tokenDOList?.size
            this.tokenHoldings = tokenHoldings
        }

        return Result.success(addressInfoResp)
    }

    override fun checkAddressIsContract(address: String): Result<Boolean> {
        return Result.success(addressRepository!!.findByAddress(address)?.isContract == 1)
    }

}
