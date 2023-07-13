package com.crypted.explorer.api.model.domain.block

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*

/**
 *
 * @author Raine.Jo
 * @date 2023-06-20 11:00:00
 */
@Document(collection = "blocks")
class BlockMongoDO : Serializable {
    @Id
    var id: ObjectId? = null
    var hash: String? = null
    var __v = 0
    var createdAt: Date? = null
    var baseFeePerGas: String? = null
    var blockReward: String? = null
    var burntFee: String? = null
    var extraData: String? = null
    var fee: String? = null
    var gasLimit: String? = null
    var gasUsed: String? = null
    var number = 0
    var parentHash: String? = null
    var referralFee: String? = null
    var rewardFee: String? = null
    var staticBlockReward: String? = null
    var timestamp = 0
    var updatedAt: Date? = null
    var transactionCount = 0

    companion object {
        private const val serialVersionUID = 8718060005385905866L
    }
}
