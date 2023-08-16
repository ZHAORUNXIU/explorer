package com.crypted.explorer.service.transaction.transfer

import com.crypted.explorer.api.model.domain.transaction.transfer.Erc20TransferMongoDO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface Erc20TransferMongoRepository : MongoRepository<Erc20TransferMongoDO, String> {

    fun countByTokenAddress(tokenAddress: String): Int

    fun findByTransactionHash(transactionHash: String): Erc20TransferMongoDO?
}
