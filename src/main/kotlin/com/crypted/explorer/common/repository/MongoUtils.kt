package com.crypted.explorer.common.repository

import com.crypted.explorer.api.model.domain.block.BlockMongoDO
import com.crypted.explorer.api.service.account.AccountService
import org.bson.Document
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
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

    fun <T : Any> getByPage(pageable: Pageable, entityType: KClass<T>, andCriteriaList: List<Criteria>? = null, orCriteriaList: List<Criteria>? = null): List<T> {
        val query = Query().with(pageable)
//        criteria?.let {
//            query.addCriteria(it)
//        }
        val criteria = Criteria()
        if (!andCriteriaList.isNullOrEmpty()) {
//            val andCriteriaQuery = Criteria().andOperator(*andCriteriaList.toTypedArray())
//            query.addCriteria(andCriteriaQuery)
            criteria.andOperator(*andCriteriaList.toTypedArray())
        }

        if (!orCriteriaList.isNullOrEmpty()) {
//            val orCriteriaQuery = Criteria().orOperator(*orCriteriaList.toTypedArray())
//            query.addCriteria(orCriteriaQuery)
            criteria.orOperator(*orCriteriaList.toTypedArray())
        }
        query.addCriteria(criteria)
        return mongoTemplate.find(query, entityType.java)
    }

//    fun <T : Any> count(entityType: KClass<T>): Int {
//        val query = Query()
//        return mongoTemplate.count(query, entityType.java).toInt()
//    }

    fun getCountWithNativeQuery(collectionName: String, andCriteriaList: List<Criteria>? = null, orCriteriaList: List<Criteria>? = null): Int {
        val nativeQuery = Document()
        nativeQuery["count"] = collectionName

        if (!andCriteriaList.isNullOrEmpty() || !orCriteriaList.isNullOrEmpty()) {
            val queryObject = Document()

            if (!andCriteriaList.isNullOrEmpty()) {
                val andCriteriaArray = andCriteriaList.map { it.criteriaObject }
                queryObject["\$and"] = andCriteriaArray
            }

            if (!orCriteriaList.isNullOrEmpty()) {
                val orCriteriaArray = orCriteriaList.map { it.criteriaObject }
                queryObject["\$or"] = orCriteriaArray
            }

            nativeQuery["query"] = queryObject
        }

        val countResult = mongoTemplate.executeCommand(nativeQuery)
        return countResult["n"].toString().toInt()
    }
}