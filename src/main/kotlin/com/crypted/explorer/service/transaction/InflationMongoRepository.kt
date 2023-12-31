package com.crypted.explorer.service.transaction

import com.crypted.explorer.api.model.domain.transaction.InflationMongoDO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface InflationMongoRepository : MongoRepository<InflationMongoDO, String> {
}
