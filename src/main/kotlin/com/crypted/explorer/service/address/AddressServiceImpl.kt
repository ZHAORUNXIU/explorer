package com.crypted.explorer.service.address

import com.crypted.explorer.api.model.domain.address.AddressDO
import com.crypted.explorer.api.service.address.AddressService
import org.springframework.stereotype.Service
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.gateway.model.resp.address.AddressRankingResp
import com.crypted.explorer.gateway.model.vo.address.AddressRankingVO
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import javax.annotation.Resource

@Service
class AddressServiceImpl : AddressService {

    @Resource
    private val addressRepository: AddressRepository? = null
    override fun getRankingByPage(pageNumber: Int, pageSize: Int): Result<AddressRankingResp?> {

        val pageable: Pageable = PageRequest.of(pageNumber, pageSize)
        val addressDOList: List<AddressDO?> = addressRepository!!.findAll(pageable).content
//        val addressRanking: List<AddressRankingVO> = addressDOList?.map { item ->
//            val addressRankingVO = AddressRankingVO()
////            addressRankingVO.Id = item?.id
////            addressRankingVO.brandName = item?.name
//            addressRankingVO
//        }.toList()
//
//        var totalAddress: Long = addressRepository!!.count()
//        var totalPage: Int = totalAddress

        return Result.success(null)
    }
}
