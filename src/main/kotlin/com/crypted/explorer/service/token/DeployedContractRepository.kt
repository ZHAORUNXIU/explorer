package com.crypted.explorer.service.token

import com.crypted.explorer.api.model.domain.token.DeployedContractDO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author Raine.Jo
 * @date 2023-06-28 17:00:00
 */
@Repository
interface DeployedContractRepository : JpaRepository<DeployedContractDO, Long> {
}
