package com.crypted.explorer.service.block

import com.crypted.explorer.api.model.domain.block.BlockMongoDO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BlockMongoRepository : MongoRepository<BlockMongoDO?, String?> {

    //    Optional<BlockMongoDO> findByUserId(Long userId);
}
