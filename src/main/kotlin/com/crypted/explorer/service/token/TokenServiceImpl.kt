package com.crypted.explorer.service.token

import com.crypted.explorer.api.model.domain.token.TokenDO
import com.crypted.explorer.common.constant.TokenType
import com.crypted.explorer.api.service.token.TokenService
import com.crypted.explorer.common.constant.AddressCode
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.common.util.MathUtils
import com.crypted.explorer.gateway.model.resp.token.TokenInfoResp
import com.crypted.explorer.gateway.model.resp.token.TokenListResp
import com.crypted.explorer.gateway.model.vo.token.TokenListVO
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import javax.annotation.Resource
import kotlin.streams.toList

@Service
class TokenServiceImpl : TokenService {

    private val SORT_BY_ID = "id"

    @Resource
    private val tokenRepository: TokenRepository? = null

    @Resource
    private val deployedContractRepository: DeployedContractRepository? = null

    @Resource
    private val contractRepository: ContractRepository? = null

    @Resource
    private val erc20HoldRepository: Erc20HoldRepository? = null

    @Resource
    private val erc721HoldRepository: Erc721HoldRepository? = null

    @Resource
    private val erc1155HoldRepository: Erc1155HoldRepository? = null

    override fun getTokenListByAddress(address: String): Result<List<TokenDO>?> {

        // EOA
        val tokenDOList: List<TokenDO>? = tokenRepository!!.findByAddress(address)

        return Result.success(tokenDOList)
    }

    override fun getListByPage(pageNumber: Int, pageSize: Int): Result<TokenListResp?> {

        val pageable: Pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.DESC, SORT_BY_ID)
        val tokenDOList: List<TokenDO?> = tokenRepository!!.findAll(pageable).content

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

        val tokenDOList: List<TokenDO>? = tokenRepository!!.findByAddress(address)
        if (tokenDOList?.size == 1)
            return Result.failure(AddressCode.NOT_CONTRACT_ADDRESS)

        val tokenDO: TokenDO? = tokenDOList?.get(1)

//        tokenDOList.stream().map { tokenDO ->
//            val tokenListVO = TokenListVO()
//            tokenListVO.address = tokenDO!!.address
//            tokenListVO.symbol = tokenDO.symbol
//            tokenListVO.name = getTokenName(tokenDO).data
//            tokenListVO.imageUrl = tokenDO.image
//            tokenListVO
//        }.toList()

        val tokenInfoResp = TokenInfoResp().apply {
            this.name = tokenDO?.let { getTokenName(it).data }
            this.symbol = tokenDO?.symbol
            this.address = tokenDO?.address
            this.totalSupply = tokenDO?.supply
//            this.totalTransfer = tokenDO.  //Int
//            this.officialSite = tokenDO.
            this.imageUrl = tokenDO?.image
        }

        return Result.success(tokenInfoResp)
    }

    override fun getTokenName(tokenDO: TokenDO): Result<String?> {

        val contractId = deployedContractRepository!!.findById(tokenDO.deployedContractId!!).get().contractId

        return Result.success(contractId?.let { contractRepository!!.findById(it).get().name })
    }

    override fun getTokenBalance(tokenDO: TokenDO): Result<String?> {

        val balance: String? = when (tokenDO.type) {
            TokenType.ERC20 ->  tokenDO.id?.let { erc20HoldRepository!!.findByTokenId(it).balance }
            TokenType.ERC721 ->  tokenDO.id?.let { erc721HoldRepository!!.findByTokenId(it).balance }
            TokenType.ERC1155 ->  tokenDO.id?.let { erc1155HoldRepository!!.findByTokenId(it).balance }
            else -> null
        }

        return Result.success(balance)
    }

}
