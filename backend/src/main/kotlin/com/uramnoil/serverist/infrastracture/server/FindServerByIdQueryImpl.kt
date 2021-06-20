package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.queries.FindServerByIdQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class FindServerByIdQueryImpl : FindServerByIdQuery {
    override suspend fun execute(id: UUID): Server? {
        return newSuspendedTransaction {
            Servers.select { Servers.id eq id }.firstOrNull()
        }?.let(ResultRow::toApplicationServer)
    }
}