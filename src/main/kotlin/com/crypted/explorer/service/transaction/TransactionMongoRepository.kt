package com.crypted.explorer.service.transaction

import com.crypted.explorer.api.model.domain.transaction.TransactionMongoDO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionMongoRepository : MongoRepository<TransactionMongoDO?, String?> {

    //    Optional<TransactionMongoDO> findByUserId(Long userId);
}
