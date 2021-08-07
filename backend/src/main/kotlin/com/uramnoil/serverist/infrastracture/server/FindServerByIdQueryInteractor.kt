package com.uramnoil.serverist.infrastracture.server

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.queries.FindServerByIdQueryInputPort
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class FindServerByIdQueryInteractor : FindServerByIdQueryInputPort {
    override suspend fun execute(id: Uuid) = kotlin.runCatching {
        newSuspendedTransaction {
            Servers.select { Servers.id eq id }.firstOrNull()
        }?.let(ResultRow::toApplicationServer)
    }
}