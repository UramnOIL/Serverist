package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.queries.CheckServerOwnerService
import com.uramnoil.serverist.application.server.queries.CheckServerOwnerServiceDto
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedCheckServerOwnerService : CheckServerOwnerService {
    override suspend fun execute(dto: CheckServerOwnerServiceDto): Boolean = newSuspendedTransaction {
        val result = Servers.select { (Servers.id eq dto.ownerId) and (Servers.owner eq dto.ownerId) }
        result.firstOrNull() != null
    }
}