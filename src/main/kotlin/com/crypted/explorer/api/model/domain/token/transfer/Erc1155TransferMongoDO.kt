package com.crypted.explorer.api.model.domain.token.transfer

import org.springframework.data.mongodb.core.mapping.Document


/**
 *
 * @author Raine.Jo
 * @date 2023-07-10 16:00:00
 */
@Document(collection = "erc1155_transfers")
class Erc1155TransferMongoDO : TokenTransferMongoDO() {
}
