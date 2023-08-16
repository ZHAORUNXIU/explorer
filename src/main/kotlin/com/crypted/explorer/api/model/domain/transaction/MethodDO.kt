package com.crypted.explorer.api.model.domain.transaction

import com.crypted.explorer.common.constant.TokenType
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 *
 * @author Raine.Jo
 * @date 2023-08-11 14:20:00
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "method")
class MethodDO : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "signature")
    var signature: String? = null

    @Column(name = "label")
    var label: String? = null

    @Column(name = "desc")
    var desc: String? = null

    @Column(name = "created_at")
    var createdAt: Date? = null

    @Column(name = "updated_at")
    var updatedAt: Date? = null
}
