package com.crypted.explorer.service.token

import com.crypted.explorer.api.model.domain.token.transfer.Erc721TransferMongoDO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface Erc721TransferMongoRepository : MongoRepository<Erc721TransferMongoDO, String> {

    fun countByTokenAddress(tokenAddress: String): Int


}
