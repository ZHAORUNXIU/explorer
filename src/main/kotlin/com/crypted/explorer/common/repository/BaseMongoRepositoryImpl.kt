package com.crypted.explorer.common.repository

import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.repository.query.MongoEntityInformation
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable

@NoRepositoryBean
class BaseMongoRepositoryImpl<T, ID : Serializable?>(
        private val entityInformation: MongoEntityInformation<T, ID>,
        private val mongoOperations: MongoOperations
) : SimpleMongoRepository<T, ID>(entityInformation, mongoOperations), BaseMongoRepository<T, ID> {
    companion object {
        private val LOG = LoggerFactory.getLogger(BaseMongoRepositoryImpl::class.java)
    }

}
