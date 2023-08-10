package com.crypted.explorer.service.transaction

import com.crypted.explorer.api.model.domain.transaction.InflationMongoDO
import com.crypted.explorer.api.model.domain.transaction.TransactionMongoDO
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface InflationMongoRepository : MongoRepository<InflationMongoDO, String> {

    fun findByCheckpointIn(checkpoints: List<ObjectId>): List<InflationMongoDO>
}
