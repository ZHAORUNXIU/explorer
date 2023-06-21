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
    private val id: ObjectId? = null
    private val hash: String? = null
    private val __v = 0
    private val createdAt: Date? = null
    private val blockHash: String? = null
    private val blockNumber = 0
    private val burntFee: String? = null
    private val contractAddress: String? = null
    private val effectiveGasPrice: String? = null
    private val eventLogs: List<Any>? = null
    private val fee: String? = null
    private val from: String? = null
    private val functionSignature: String? = null
    private val gasLimit: String? = null
    private val gasPrice: String? = null
    private val gasUsed: String? = null
    private val inputData: String? = null
    private val nonce = 0
    private val referralFee: String? = null
    private val rewardFee: String? = null
    private val to: String? = null
    private val transactionIndex = 0
    private val updatedAt: Date? = null
    private val value: String? = null

    companion object {
        private const val serialVersionUID = 4429641190773945715L
    }
}
