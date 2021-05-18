package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.server.queries.FindServersByOwnerQuery
import com.uramnoil.serverist.application.server.queries.FindServersByOwnerQueryDto
import com.uramnoil.serverist.application.user.queries.Server
import com.uramnoil.serverist.infrastracture.server.Servers
import com.uramnoil.serverist.infrastracture.server.toKotlinInstance
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindServersByOwnerQuery : FindServersByOwnerQuery {
    override suspend fun execute(dto: FindServersByOwnerQueryDto): List<Server> = newSuspendedTransaction {
        Servers.select { Servers.owner eq dto.ownerId }.map {
            Server(
                id = it[Servers.id].value,
                createdAt = it[Servers.createdAt].toKotlinInstance(),
                name = it[Servers.name],
                address = it[Servers.address],
                port = it[Servers.port],
                description = it[Servers.description]
            )
        }
    }
}