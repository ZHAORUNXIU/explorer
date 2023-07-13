package com.crypted.explorer.api.model.domain.token

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import javax.persistence.*

/**
 *
 * @author Raine.Jo
 * @date 2023-06-29 10:20:00
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "erc721_hold")
class Erc721HoldDO : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = 0

//    @Column(name = "token_id")
//    var tokenId: Long? = 0

    @Column(name = "holder")
    var holder: String? = null

    @Column(name = "token_address")
    var tokenAddress: String? = null

    @Column(name = "balance")
    var balance: String? = null

//    @Column(name = "token_id_list")
//    var tokenIdList: String? = null
}
