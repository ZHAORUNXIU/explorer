package com.crypted.explorer.service.token

import com.crypted.explorer.api.model.domain.token.Erc1155HoldDO
import com.crypted.explorer.api.model.domain.token.Erc20HoldDO
import com.crypted.explorer.api.model.domain.token.Erc721HoldDO
import com.crypted.explorer.api.model.domain.token.TokenDO
import com.crypted.explorer.api.service.token.TokenService
import com.crypted.explorer.common.constant.AccountCode
import com.crypted.explorer.common.constant.SearchType
import com.crypted.explorer.common.constant.TokenType
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.MathUtils
import com.crypted.explorer.common.util.Text
import com.crypted.explorer.gateway.model.resp.token.TokenInfoResp
import com.crypted.explorer.gateway.model.resp.token.TokenListResp
import com.crypted.explorer.gateway.model.vo.token.TokenVO
import com.crypted.explorer.gateway.model.vo.token.TokenListVO
import com.crypted.explorer.service.search.SearchServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import javax.annotation.Resource
import kotlin.streams.toList

@Service
class TokenServiceImpl(
    private val tokenRepository: TokenRepository,
    private val deployedContractRepository: DeployedContractRepository,
    private val contractRepository: ContractRepository,
    private val erc20HoldRepository: Erc20HoldRepository,
    private val erc721HoldRepository: Erc721HoldRepository,
    private val erc1155HoldRepository: Erc1155HoldRepository,
    private val erc20TransferMongoRepository: Erc20TransferMongoRepository,
    private val erc721TransferMongoRepository: Erc721TransferMongoRepository,
    private val erc1155TransferMongoRepository: Erc1155TransferMongoRepository
) : TokenService {

    companion object {
        private val LOG = LoggerFactory.getLogger(TokenServiceImpl::class.java)
    }

    private val SORT_BY_ID = "id"

    override fun getTokenHoldingsByHolder(holder: String): Result<List<TokenVO>?> {

        // EOA & GA
        // erc20 balance -> Quantity
        val erc20HoldDOList: List<Erc20HoldDO>? = erc20HoldRepository.findByHolder(holder)
        // erc721 and erc1155 balance -> Unique Tokens
//        val erc721HoldCountingMap: Map<Long, Long>? = erc721HoldRepository.findByHolder(holder)
//            ?.stream()?.collect(Collectors.groupingBy(Erc721HoldDO::tokenId, Collectors.counting()))
//        val erc1155HoldCountingMap: Map<Long, Long>? = erc1155HoldRepository.findByHolder(holder)
//            ?.stream()?.collect(Collectors.groupingBy(Erc1155HoldDO::tokenId, Collectors.counting()))
//        val mergedMap = mutableMapOf<Long, Long>()
//        erc721HoldCountingMap?.let { mergedMap.putAll(it) }
//        erc1155HoldCountingMap?.let { mergedMap.putAll(it) }

        val tokenHoldings: List<TokenVO> = listOfNotNull(
            // erc20
            erc20HoldDOList?.stream()?.map { erc20HoldDO ->
                val tokenVO = TokenVO()
                val tokenDO: TokenDO? = erc20HoldDO.holder?.let { tokenRepository.findByAddress(it) }
                tokenVO.name = tokenDO?.let { this.getTokenName(it).data }
                tokenVO.tokenSymbol = tokenDO?.symbol
                tokenVO.tokenBalance = erc20HoldDO.balance
                tokenVO.imageUrl = tokenDO?.image
                tokenVO
            }?.toList(),
            // erc721 and erc1155
//            mergedMap.entries.stream()
//                .map { entry ->
//                    val tokenVO = TokenVO()
//                    val tokenId = entry.key
//                    val tokenDO: TokenDO = tokenId.let { tokenRepository.findById(it).get() }
//                    tokenVO.name = tokenDO.let { this.getTokenName(it).data }
//                    tokenVO.tokenSymbol = tokenDO.symbol
//                    tokenVO.tokenBalance = entry.value.toString()
//                    tokenVO.imageUrl = tokenDO.image
//                    tokenVO
//                }?.toList(),
        ).flatten().toList()

        return Result.success(tokenHoldings)
    }

    override fun getListByPage(pageNumber: Int, pageSize: Int): Result<TokenListResp?> {

        val pageable: Pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.DESC, SORT_BY_ID)
        val tokenDOList: List<TokenDO?> = tokenRepository.findAll(pageable).content

        val tokenList: List<TokenListVO?> = tokenDOList.stream().map { tokenDO ->
            val tokenListVO = TokenListVO()
            tokenListVO.address = tokenDO!!.address
            tokenListVO.symbol = tokenDO.symbol
            tokenListVO.name = getTokenName(tokenDO).data
            tokenListVO.imageUrl = tokenDO.image
            tokenListVO
        }.toList()

        val totalToken: Int = tokenRepository.count().toInt()

        val tokenListResp = TokenListResp().apply {
            this.totalPage = MathUtils.ceilDiv(totalToken, pageSize)
            this.totalToken = totalToken
            this.tokenList = tokenList
        }

        return Result.success(tokenListResp)
    }

    override fun getInfoByContractAddress(address: String): Result<TokenInfoResp?> {

        val tokenDO: TokenDO? = tokenRepository.findByAddress(address)
        tokenDO?.let {
            val tokenInfoResp = TokenInfoResp().apply {
                this.name = tokenDO.let { getTokenName(it).data }
                this.symbol = tokenDO.symbol
                this.address = tokenDO.address
                this.totalSupply = tokenDO.supply
                this.totalTransfer = getTotalTransfers(tokenDO)
                this.officialSite = tokenDO.site
                this.imageUrl = tokenDO.image
            }
            return Result.success(tokenInfoResp)
        } ?: run {
            return Result.failure(AccountCode.NOT_CONTRACT_ACCOUNT.code, AccountCode.NOT_CONTRACT_ACCOUNT.message)
        }
    }

    private fun getTokenName(tokenDO: TokenDO): Result<String?> {

        val contractId = deployedContractRepository.findById(tokenDO.deployedContractId!!).get().contractId

        return Result.success(contractId?.let { contractRepository.findById(it).get().name })
    }

    private fun getTotalTransfers(tokenDO: TokenDO): Int? {

        val type = tokenDO.type?.let { Text.cleanAndLowercase(it) }
        val address = tokenDO.address

        return when (type) {
            TokenType.ERC20.value -> address?.let { erc20TransferMongoRepository.countByTokenAddress(it) }
            TokenType.ERC721.value -> address?.let { erc721TransferMongoRepository.countByTokenAddress(it) }
            TokenType.ERC1155.value -> address?.let { erc1155TransferMongoRepository.countByTokenAddress(it) }
            else -> 0
        }
    }
}
