package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.queries.FindAllServerQueryDto
import com.uramnoil.serverist.application.server.queries.FindAllServersQuery
import com.uramnoil.serverist.application.server.queries.OrderBy
import com.uramnoil.serverist.infrastracture.user.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindAllServersQuery : FindAllServersQuery {
    override suspend fun execute(dto: FindAllServerQueryDto): List<Server> {
        return newSuspendedTransaction {
            val query = (Servers innerJoin Users).slice(
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
            ).selectAll().orderBy(
                when (dto.orderBy) {
                    OrderBy.CreatedAt -> Servers.createdAt
                },
                when (dto.sort) {
                    Sort.Asc -> SortOrder.ASC
                    Sort.Desc -> SortOrder.DESC
                }
            ).limit(dto.limit, offset = dto.offset)
            query.map(ResultRow::toApplicationServer)
        }
    }
}