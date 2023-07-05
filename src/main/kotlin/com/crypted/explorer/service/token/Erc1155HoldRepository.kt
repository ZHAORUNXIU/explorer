package com.crypted.explorer.service.token

import com.crypted.explorer.api.model.domain.token.Erc1155HoldDO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author Raine.Jo
 * @date 2023-06-29 10:20:00
 */
@Repository
interface Erc1155HoldRepository : JpaRepository<Erc1155HoldDO, Long> {

    fun findByHolder(holder: String): List<Erc1155HoldDO>?

}
