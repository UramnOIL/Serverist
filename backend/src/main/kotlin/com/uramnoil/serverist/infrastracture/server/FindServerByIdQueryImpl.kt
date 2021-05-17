package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.kernel.User
import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.queries.FindServerByIdDto
import com.uramnoil.serverist.application.server.queries.FindServerByIdQuery
import com.uramnoil.serverist.infrastracture.user.Users
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class FindServerByIdQueryImpl : FindServerByIdQuery {
    override suspend fun execute(dto: FindServerByIdDto): Server? {
        val result = newSuspendedTransaction {
            (Servers innerJoin Users).slice(
                Servers.id,
                Servers.createdAt,
                Servers.name,
                Servers.address,
                Servers.port,
                Servers.description,
                Users.id,
                Users.accountId,
                Users.name,
                Users.description,
            ).select { Servers.id eq dto.id }.firstOrNull()
        } ?: return null

        return Server(
            id = result[Servers.id].value,
            createdAt = result[Servers.createdAt].toKotlinInstance(),
            owner = User(
                id = result[Users.id].value,
                accountId = result[Users.accountId],
                name = result[Users.name],
                description = result[Users.description]
            ),
            name = result[Servers.name],
            address = result[Servers.address],
            port = result[Servers.port],
            description = result[Servers.description]
        )
    }
}