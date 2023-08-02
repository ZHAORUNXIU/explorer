package com.crypted.explorer.api.model.domain.transaction

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
@Document(collection = "transactions")
class TransactionMongoDO : Serializable {
    @Id
    var id: ObjectId? = null
    var hash: String? = null
    var __v = 0
    var createdAt: Date? = null
    var blockHash: String? = null
    var blockNumber: Long = 0L
    var burntFee: String? = null
    var contractAddress: String? = null
    var effectiveGasPrice: String? = null
    var eventLogs: List<Any>? = null
    var fee: String? = null
    var from: String? = null
    var functionSignature: String? = null
    var gasLimit: String? = null
    var gasPrice: String? = null
    var gasUsed: String? = null
    var inputData: String? = null
    var nonce = 0
    var referralFee: String? = null
    var rewardFee: String? = null
    var to: String? = null
    var transactionIndex = 0
    var updatedAt: Date? = null
    var value: String? = null
    var functionName: String? = null
    var status: Int? = null

    companion object {
        private const val serialVersionUID = 4429641190773945715L
    }
}
