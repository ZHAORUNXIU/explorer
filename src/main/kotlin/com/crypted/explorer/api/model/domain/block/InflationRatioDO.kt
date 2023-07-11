package com.crypted.explorer.api.model.domain.block

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*

/**
 *
 * @author Raine.Jo
 * @date 2023-07-10 14:50:00
 */
@Document(collection = "inflation_ratios")
class InflationRatioDO : Serializable {
    @Id
    var id: ObjectId? = null
    var blockNumber = 0
    var ratio: String? = null
    var staticBlockReward: String? = null
    var createdAt: Date? = null
    var __v = 0
}
