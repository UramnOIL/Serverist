package com.uramnoil.serverist.server.infrastructure.application.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.server.application.Server
import com.uramnoil.serverist.server.application.queries.FindServerByIdQueryUseCaseInputPort
import com.uramnoil.serverist.server.infrastructure.Servers
import com.uramnoil.serverist.server.infrastructure.toApplicationServer
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class FindServerByIdQueryUseCaseInteractor : FindServerByIdQueryUseCaseInputPort {
    override suspend fun execute(id: Uuid): Result<Server?> {
        val row = kotlin.runCatching {
            newSuspendedTransaction {
                Servers.select { Servers.id eq id }.firstOrNull()
            }
        }
        return row.map { it?.toApplicationServer() }
    }
}