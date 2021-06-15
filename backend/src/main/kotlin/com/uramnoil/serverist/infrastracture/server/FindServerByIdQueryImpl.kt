package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.queries.FindServerByIdDto
import com.uramnoil.serverist.application.server.queries.FindServerByIdQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class FindServerByIdQueryImpl : FindServerByIdQuery {
    override suspend fun execute(dto: FindServerByIdDto): Server? {
        return newSuspendedTransaction {
            Servers.select { Servers.id eq dto.id }.firstOrNull()
        }?.let(ResultRow::toApplicationServer)
    }
}