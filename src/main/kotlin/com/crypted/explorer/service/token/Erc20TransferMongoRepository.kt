package com.crypted.explorer.service.token

import com.crypted.explorer.api.model.domain.token.transfer.Erc20TransferMongoDO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface Erc20TransferMongoRepository : MongoRepository<Erc20TransferMongoDO, String> {

    fun countByTokenAddress(tokenAddress: String): Int


}
