package com.crypted.explorer.service.token

import com.crypted.explorer.api.model.domain.token.Erc20HoldDO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author Raine.Jo
 * @date 2023-06-29 10:20:00
 */
@Repository
interface Erc20HoldRepository : JpaRepository<Erc20HoldDO, Long> {

    fun findByHolder(holder: String): List<Erc20HoldDO>?

}
