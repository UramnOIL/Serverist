package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.queries.FindAllServerQueryDto
import com.uramnoil.serverist.application.server.queries.FindAllServersQuery
import com.uramnoil.serverist.application.server.queries.OrderBy
import com.uramnoil.serverist.application.server.queries.Sort
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindAllServersQuery : FindAllServersQuery {
    override suspend fun execute(dto: FindAllServerQueryDto): List<Server> {
        return newSuspendedTransaction {
            val query = Servers.selectAll().orderBy(
                when (dto.orderBy) {
                    OrderBy.CreatedAt -> Servers.createdAt
                },
                when (dto.sort) {
                    Sort.Asc -> SortOrder.ASC
                    Sort.Desc -> SortOrder.DESC
                }
            ).limit(dto.limit, offset = dto.offset)
            query.map {
                ExposedServerFactory.create(it).let { server ->
                    Server(
                        id = server.id.value,
                        createdAt = server.createdAt.value,
                        ownerId = server.ownerId.value,
                        name = server.name.value,
                        address = server.address.value,
                        port = server.port.value,
                        description = server.description.value
                    )
                }
            }
        }
    }
}