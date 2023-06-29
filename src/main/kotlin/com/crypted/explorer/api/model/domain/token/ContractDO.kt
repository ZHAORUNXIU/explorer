package com.crypted.explorer.api.model.domain.token

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import javax.persistence.*

/**
 *
 * @author Raine.Jo
 * @date 2023-06-28 17:00:00
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "contract")
class ContractDO : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "abi")
    var abi: String? = null

    @Column(name = "code")
    var code: String? = null

    @Column(name = "file_path")
    var filePath: String? = null

    @Column(name = "is_token")
    var isToken: Int? = 0

    @Column(name = "partner_id")
    var partnerId: Int? = 0

}
