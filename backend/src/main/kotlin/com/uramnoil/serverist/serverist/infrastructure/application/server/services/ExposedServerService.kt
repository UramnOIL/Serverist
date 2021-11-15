package com.uramnoil.serverist.serverist.infrastructure.application.server.services

import com.uramnoil.serverist.serverist.application.server.services.ServerService
import com.uramnoil.serverist.serverist.infrastructure.Servers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class ExposedServerService : ServerService {
    override suspend fun checkUserIsOwnerOfServer(ownerId: UUID, serverId: UUID): Result<Boolean> = kotlin.runCatching {
        newSuspendedTransaction {
            val result = Servers.select { (Servers.id eq serverId) and (Servers.ownerId eq ownerId) }
            result.firstOrNull() != null
        }
    }
}