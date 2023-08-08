package com.crypted.explorer.service.token

import com.crypted.explorer.api.model.domain.token.TokenDO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author Raine.Jo
 * @date 2023-06-26 13:00:00
 */
@Repository
interface TokenRepository : JpaRepository<TokenDO, Long> {

    fun findByAddress(address: String): TokenDO?

    fun countByIsExposedIn(visibilities: List<String>): Int
}
