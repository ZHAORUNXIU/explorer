package com.crypted.explorer.service.transaction

import com.crypted.explorer.api.model.domain.transaction.TransactionMongoDO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionMongoRepository : MongoRepository<TransactionMongoDO, String> {

    fun findByFromOrTo(from: String, to: String, pageable: Pageable): Page<TransactionMongoDO>

    fun findByFromOrToAndStatus(from: String, to: String, status: Int, pageable: Pageable): Page<TransactionMongoDO>

    fun findByTo(to: String, pageable: Pageable): Page<TransactionMongoDO>

    fun findByToAndStatus(to: String, status: Int, pageable: Pageable): Page<TransactionMongoDO>

    fun findByBlockNumber(blockNumber: Long, pageable: Pageable): Page<TransactionMongoDO>

    fun findByBlockNumberAndStatus(blockNumber: Long, status: Int, pageable: Pageable): Page<TransactionMongoDO>

    fun findByHash(hash: String): TransactionMongoDO

    fun countByFromOrToAndStatus(from: String, to: String, status: Int): Int

    fun countByFromOrTo(from: String, to: String): Int

    fun countByTo(to: String): Int

    fun countByToAndStatus(to: String, status: Int): Int

    fun countByBlockNumberAndStatus(blockNumber: Long, status: Int): Int

    fun countByBlockNumber(blockNumber: Long): Int

    fun countByStatus(status: Int): Int

}
