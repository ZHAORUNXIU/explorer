package com.crypted.explorer.service.block

import com.crypted.explorer.api.model.domain.block.BlockMongoDO
import com.crypted.explorer.api.model.domain.block.InflationRatioDO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface InflationRatioMongoRepository : MongoRepository<InflationRatioDO, String> {
}
