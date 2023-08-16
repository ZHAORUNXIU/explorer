package com.crypted.explorer.api.model.domain.transaction.transfer

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.io.Serializable
import java.util.*

abstract class TokenTransferMongoDO : Serializable {
    @Id
    var id: ObjectId? = null
    var blockNumber: Long = 0L
    var transactionHash: String? = null
    var logIndex = 0
    var functionName: String? = null
    var functionSignature: String? = null
    var from: String? = null
    var to: String? = null
    var value: String? = null
    var tokenAddress: String? = null
    var createdAt: Date? = null
    var updatedAt: Date? = null
    var blockTimestamp: Long? = 0
}