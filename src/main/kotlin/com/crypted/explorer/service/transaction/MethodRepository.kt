package com.crypted.explorer.service.transaction

import com.crypted.explorer.api.model.domain.transaction.MethodDO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author Raine.Jo
 * @date 2023-08-11 14:20:00
 */
@Repository
interface MethodRepository : JpaRepository<MethodDO, Long> {
}
