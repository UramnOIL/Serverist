package com.uramnoil.serverist.serverist.infrastructure.application.server.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.serverist.application.server.Server
import com.uramnoil.serverist.serverist.application.server.queries.FindServerByIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.infrastructure.Servers
import com.uramnoil.serverist.serverist.infrastructure.toApplicationServer
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