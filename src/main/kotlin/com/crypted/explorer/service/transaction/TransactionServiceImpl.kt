package com.crypted.explorer.service.transaction

import com.crypted.explorer.api.model.domain.block.BlockMongoDO
import com.crypted.explorer.api.model.domain.transaction.TransactionMongoDO
import com.crypted.explorer.api.service.transaction.TransactionService
import org.springframework.stereotype.Service
import com.crypted.explorer.common.model.Result
import com.crypted.explorer.gateway.model.resp.transaction.TransactionListResp
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import javax.annotation.Resource

@Service
class TransactionServiceImpl : TransactionService {

    @Resource
    private val transactionMongoRepository: TransactionMongoRepository? = null
    override fun getListByPage(pageNumber: Int, pageSize: Int): Result<TransactionListResp?> {

        val pageable: Pageable = PageRequest.of(pageNumber, pageSize)
        val transactionMongoDOList: List<TransactionMongoDO?> = transactionMongoRepository!!.findAll(pageable).content

        return Result.success(null)
    }
}
