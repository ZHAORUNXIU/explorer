package com.crypted.explorer.api.model.domain.account

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 *
 * @author Raine.Jo
 * @date 2023-06-20 11:00:00
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "account")
class AccountDO : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "address")
    var address: String? = null

    @Column(name = "balance")
    var balance: String? = null

    @Column(name = "nonce")
    var nonce: Int? = 0

//    @Column(name = "public_name")
//    var publicName: String? = null

    @Column(name = "is_contract")
    var isContract: Int? = 0

    @Column(name = "created_at")
    var createdAt: Date? = null

    @Column(name = "updated_at")
    var updatedAt: Date? = null

    companion object {
        private const val serialVersionUID = -750985702302517041L
    }
}
