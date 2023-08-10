package com.crypted.explorer.api.model.domain.transaction

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*

/**
 *
 * @author Raine.Jo
 * @date 2023-08-10 10:00:00
 */
@Document(collection = "checkpoints")
class CheckpointMongoDO : Serializable {
    @Id
    var id: ObjectId? = null
    var blockNumber: Long? = 0L
    var createdAt: Date? = null
    var updatedAt: Date? = null
    var __v: Int? = 0
}
