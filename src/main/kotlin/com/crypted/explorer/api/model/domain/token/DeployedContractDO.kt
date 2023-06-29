package com.crypted.explorer.api.model.domain.token

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 *
 * @author Raine.Jo
 * @date 2023-06-28 17:00:00
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "deployed_contract")
class DeployedContractDO : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "address")
    var address: String? = null

    @Column(name = "supply")
    var deployer: String? = null

    @Column(name = "deployed_at")
    var deployedAt: String? = null

    @Column(name = "file_path")
    var filePath: String? = null

    @Column(name = "created_at")
    var createdAt: Date? = null

    @Column(name = "updated_at")
    var updatedAt: Date? = null

    @Column(name = "account_id")
    var accountId: Long? = null

    @Column(name = "contract_id")
    var contractId: Long? = null

}
