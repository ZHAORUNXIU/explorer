package com.crypted.explorer.api.model.domain.token

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable


/**
 *
 * @author Raine.Jo
 * @date 2023-07-10 16:00:00
 */
@Document(collection = "erc721_transfers")
class Erc721TransferMongoDO : Serializable {
    @Id
    var id: ObjectId? = null
    var blockNumber = 0
    var transactionHash: String? = null
    var logIndex = 0
    var functionName: String? = null
    var from: String? = null
    var to: String? = null
    var value: String? = null
    var tokenAddress: String? = null
}
