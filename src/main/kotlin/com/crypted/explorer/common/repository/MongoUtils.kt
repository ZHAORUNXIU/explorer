package com.crypted.explorer.common.repository

import com.crypted.explorer.api.model.domain.block.BlockMongoDO
import com.crypted.explorer.api.service.account.AccountService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.annotation.Resource
import kotlin.reflect.KClass

@Component
class MongoUtils(private val mongoTemplate: MongoTemplate) {

//    @Resource
//    private val mongoTemplate: MongoTemplate? = null

    fun <T : Any> getByPage(pageable: Pageable, entityType: KClass<T>): List<T> {
        val query = Query().with(pageable)
        return mongoTemplate.find(query, entityType.java)
    }

    fun <T : Any> getByPage(page: Int, pagePerSize: Int, entityType: KClass<T>): List<T> {
        val skip = (page - 1) * pagePerSize
        val query = Query()
            .skip(skip.toLong())
            .limit(pagePerSize + skip)
        query.with(Sort.by("number").descending())
        return mongoTemplate.find(query, entityType.java)
    }

    fun <T : Any> count(entityType: KClass<T>): Int {
        val query = Query()
        return mongoTemplate.count(query, entityType.java).toInt()
    }
}