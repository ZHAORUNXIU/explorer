package com.crypted.explorer.service.token

import com.crypted.explorer.api.model.domain.token.transfer.Erc1155TransferMongoDO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface Erc1155TransferMongoRepository : MongoRepository<Erc1155TransferMongoDO, String> {

    fun countByTokenAddress(tokenAddress: String): Int


}
