package com.crypted.explorer.api.model.domain.token

import com.crypted.explorer.common.constant.TokenType
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 *
 * @author Raine.Jo
 * @date 2023-06-26 13:00:00
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "token")
class TokenDO : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "address")
    var address: String? = null

    @Column(name = "supply")
    var supply: String? = null

    @Column(name = "info")
    var info: Int? = 0

    @Column(name = "symbol")
    var symbol: String? = null

    @Column(name = "image")
    var image: String? = null

    @Column(name = "officialSite")
    var officialSite: String? = null

    @Column(name = "type")
    var type: TokenType? = null

    @Column(name = "deployed_contract_id")
    var deployedContractId: Long? = 0

    @Column(name = "created_at")
    var createdAt: Date? = null

    @Column(name = "updated_at")
    var updatedAt: Date? = null

    companion object {
        private const val serialVersionUID = -750985702302517041L
    }
}
