package com.crypted.explorer.service.account

import com.crypted.explorer.api.model.domain.account.AccountDO
import com.crypted.explorer.api.service.account.AccountService
import com.crypted.explorer.api.service.block.BlockService
import com.crypted.explorer.api.service.token.TokenService
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.MathUtils
import com.crypted.explorer.gateway.model.resp.account.AccountInfoResp
import com.crypted.explorer.gateway.model.resp.account.AccountRankingResp
import com.crypted.explorer.gateway.model.vo.account.AccountRankingVO
import com.crypted.explorer.gateway.model.vo.token.TokenVO
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import javax.annotation.Resource
import kotlin.streams.toList

@Service
class AccountServiceImpl : AccountService {

    private val RANKING_BY_BALANCE = "balance"

    @Value("\${account.symbol}")
    private lateinit var symbol: String

    @Resource
    private val accountRepository: AccountRepository? = null

    @Resource
    private val tokenService: TokenService? = null

    @Resource
    private val blockService: BlockService? = null

    override fun getRankingByPage(pageNumber: Int, pageSize: Int): Result<AccountRankingResp?> {

        val pageable: Pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.DESC, RANKING_BY_BALANCE)
        val accountDOList: List<AccountDO?> = accountRepository!!.findAll(pageable).content
        val totalBlockReward = blockService!!.getTotalBlockReward().data?.toDouble()
        val accountRanking: List<AccountRankingVO> = accountDOList.stream().map { accountDO ->
            val accountRankingVO = AccountRankingVO()
            accountRankingVO.address = accountDO?.address
            accountRankingVO.isContract = accountDO?.isContract == 0
            accountRankingVO.balance = accountDO?.balance
            accountRankingVO.symbol = this@AccountServiceImpl.symbol
            accountRankingVO.percentage = (totalBlockReward?.let { accountDO?.balance?.toDouble()?.div(it) }?.times(100)).toString()
            // txCount equals accountDO.nonce
            accountRankingVO.txCount = accountDO?.nonce
            accountRankingVO
        }.toList()


        val totalAddress: Int = accountRepository.count().toInt()

        val accountRankingResp = AccountRankingResp().apply {
            this.totalPage = MathUtils.ceilDiv(totalAddress, pageSize)
            this.totalAccount = totalAddress
            this.accountRanking = accountRanking
        }

        return Result.success(accountRankingResp)
    }

    override fun getInfoByAddress(address: String): Result<AccountInfoResp?> {

        val accountDO: AccountDO? = accountRepository!!.findByAddress(address)

        val tokenHoldings: List<TokenVO>? = tokenService!!.getTokenHoldingsByHolder(address).data

        val accountInfoResp = AccountInfoResp().apply {
            this.address = accountDO?.address
            this.balance = accountDO?.balance
            this.symbol = this@AccountServiceImpl.symbol
            this.tokenCount = tokenHoldings?.size
            this.tokenHoldings = tokenHoldings
        }

        return Result.success(accountInfoResp)
    }

    override fun checkAccountIsContract(address: String): Result<Boolean> {
        return Result.success(accountRepository!!.findByAddress(address)?.isContract == 1)
    }

}
