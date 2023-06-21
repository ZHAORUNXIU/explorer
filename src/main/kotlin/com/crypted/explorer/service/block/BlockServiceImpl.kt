package com.crypted.explorer.service.block

import com.crypted.explorer.api.model.domain.address.AddressDO
import com.crypted.explorer.api.model.domain.block.BlockMongoDO
import com.crypted.explorer.api.service.block.BlockService
import org.springframework.stereotype.Service
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.gateway.model.resp.block.BlockListResp
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import javax.annotation.Resource

@Service
class BlockServiceImpl : BlockService {

    @Resource
    private val blockMongoRepository: BlockMongoRepository? = null
    override fun getListByPage(pageNumber: Int, pageSize: Int): Result<BlockListResp?> {

        val pageable: Pageable = PageRequest.of(pageNumber, pageSize)
        val blockMongoDOList: List<BlockMongoDO?> = blockMongoRepository!!.findAll(pageable).content

        return Result.success(null)
    }
}
