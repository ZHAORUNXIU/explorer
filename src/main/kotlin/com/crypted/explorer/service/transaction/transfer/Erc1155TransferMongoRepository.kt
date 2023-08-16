package com.crypted.explorer.service.transaction.transfer

import com.crypted.explorer.api.model.domain.transaction.transfer.Erc1155TransferMongoDO
import com.crypted.explorer.api.model.domain.transaction.transfer.Erc721TransferMongoDO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface Erc1155TransferMongoRepository : MongoRepository<Erc1155TransferMongoDO, String> {

    fun countByTokenAddress(tokenAddress: String): Int

    fun findByTransactionHash(transactionHash: String): Erc1155TransferMongoDO?
}
