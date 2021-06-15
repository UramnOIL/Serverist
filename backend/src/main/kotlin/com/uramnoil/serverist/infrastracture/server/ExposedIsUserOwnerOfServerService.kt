package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.queries.IsUserOwnerOfServer
import com.uramnoil.serverist.application.server.queries.IsUserOwnerOfServerDto
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedIsUserOwnerOfServerService : IsUserOwnerOfServer {
    override suspend fun execute(dto: IsUserOwnerOfServerDto): Boolean = newSuspendedTransaction {
        val result = Servers.select { (Servers.id eq dto.ownerId) and (Servers.owner eq dto.ownerId) }
        result.firstOrNull() != null
    }
}