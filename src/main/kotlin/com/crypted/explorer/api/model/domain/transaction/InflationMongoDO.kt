package com.crypted.explorer.api.model.domain.transaction

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*

/**
 *
 * @author Raine.Jo
 * @date 2023-06-29 15:00:00
 */
@Document(collection = "inflations")
class InflationMongoDO : Serializable {
    @Id
    var id: ObjectId? = null
    var checkpoint: ObjectId? = null
    var blockCount: Int? = 0
    var blockInflation: String? = null
    var transactionCount: Int? = 0
    var fee: String? = null
    var burntFee: String? = null
    var referralFee: String? = null
    var rewardFee: String? = null
    var soReward: String? = null
    var communityReward: String? = null
    var devReward: String? = null
    var inflation: String? = null
    var createdAt: Date? = null
    var updatedAt: Date? = null
    var __v: Int? = 0
}
