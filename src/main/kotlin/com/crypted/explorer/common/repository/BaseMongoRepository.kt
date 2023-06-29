package com.crypted.explorer.common.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable

@NoRepositoryBean
interface BaseMongoRepository<T, ID : Serializable?> : MongoRepository<T, ID> {
}
