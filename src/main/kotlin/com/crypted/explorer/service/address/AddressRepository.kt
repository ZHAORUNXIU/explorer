package com.crypted.explorer.service.address

import com.crypted.explorer.api.model.domain.address.AddressDO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author Raine.Jo
 * @date 2023-06-20 11:00:00
 */
@Repository
interface AddressRepository : JpaRepository<AddressDO, Long> {

    fun findByAddress(address: String): AddressDO?

}
