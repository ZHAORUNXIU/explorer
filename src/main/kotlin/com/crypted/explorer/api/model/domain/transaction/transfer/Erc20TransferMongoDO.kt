package com.crypted.explorer.api.model.domain.transaction.transfer

import org.springframework.data.mongodb.core.mapping.Document


/**
 *
 * @author Raine.Jo
 * @date 2023-07-10 16:00:00
 */
@Document(collection = "erc20_transfers")
class Erc20TransferMongoDO : TokenTransferMongoDO() {
}
