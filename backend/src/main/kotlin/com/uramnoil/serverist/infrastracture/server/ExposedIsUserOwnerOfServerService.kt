package com.uramnoil.serverist.infrastracture.server

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.queries.IsUserOwnerOfServer
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedIsUserOwnerOfServerService : IsUserOwnerOfServer {
    override suspend fun execute(ownerId: Uuid, serverId: Uuid): Boolean = newSuspendedTransaction {
        val result = Servers.select { (Servers.id eq ownerId) and (Servers.owner eq ownerId) }
        result.firstOrNull() != null
    }
}