package com.crypted.explorer.service.account

import com.crypted.explorer.api.model.domain.account.AccountDO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author Raine.Jo
 * @date 2023-06-20 11:00:00
 */
@Repository
interface AccountRepository : JpaRepository<AccountDO, Long> {

    fun findByAddress(address: String): AccountDO?

}
