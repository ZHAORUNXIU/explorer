package com.crypted.explorer.api.model.vo.token

import com.crypted.explorer.common.constant.TokenType
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 *
 * @author Raine.Jo
 * @date 2023-08-11 15:35:00
 */
class TokenVO : Serializable {
    var id: Long? = null
    var address: String? = null
    var supply: String? = null
    var info: Int? = 0
    var symbol: String? = null
    var name: String? = null
    var image: String? = null
    var site: String? = null
    var type: String? = null
    var isExposed: String? = null
    var deployedContractId: Long? = 0
}
