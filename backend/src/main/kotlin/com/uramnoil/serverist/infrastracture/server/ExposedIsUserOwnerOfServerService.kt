package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.queries.IsUserOwnerOfServer
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class ExposedIsUserOwnerOfServerService : IsUserOwnerOfServer {
    override suspend fun execute(ownerId: UUID, serverId: UUID): Boolean = newSuspendedTransaction {
        val result = Servers.select { (Servers.id eq ownerId) and (Servers.owner eq ownerId) }
        result.firstOrNull() != null
    }
}