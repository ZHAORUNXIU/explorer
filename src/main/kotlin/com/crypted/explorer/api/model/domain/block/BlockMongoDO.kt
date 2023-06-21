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
    private val id: ObjectId? = null
    private val hash: String? = null
    private val __v = 0
    private val createdAt: Date? = null
    private val baseFeePerGas: String? = null
    private val blockReward: String? = null
    private val burntFee: String? = null
    private val extraData: String? = null
    private val fee: String? = null
    private val gasLimit: String? = null
    private val gasUsed: String? = null
    private val number = 0
    private val parentHash: String? = null
    private val referralFee: String? = null
    private val rewardFee: String? = null
    private val staticBlockReward: String? = null
    private val timestamp = 0
    private val updatedAt: Date? = null

    companion object {
        private const val serialVersionUID = 8718060005385905866L
    }
}
