package com.crypted.explorer.service.block

import com.crypted.explorer.api.model.domain.block.BlockMongoDO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BlockMongoRepository : MongoRepository<BlockMongoDO, String> {

    fun findByNumber(number: Int): BlockMongoDO

    fun findTopByOrderByNumberDesc(): Int

}
