package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.queries.FindServersByOwnerQuery
import com.uramnoil.serverist.application.server.queries.FindServersByOwnerQueryDto
import com.uramnoil.serverist.application.server.queries.OrderBy
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindServersByOwnerQuery : FindServersByOwnerQuery {
    override suspend fun execute(dto: FindServersByOwnerQueryDto): List<Server> = newSuspendedTransaction {
        Servers.select { Servers.owner eq dto.ownerId }.orderBy(
            when (dto.orderBy) {
                OrderBy.CreatedAt -> Servers.createdAt
            },
            when (dto.sort) {
                Sort.Asc -> SortOrder.ASC
                Sort.Desc -> SortOrder.DESC
            }
        ).limit(dto.limit, offset = dto.offset).map(ResultRow::toApplicationServer)
    }
}