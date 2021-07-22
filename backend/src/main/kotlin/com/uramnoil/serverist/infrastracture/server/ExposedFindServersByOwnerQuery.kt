package com.uramnoil.serverist.infrastracture.server

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.queries.FindServersByOwnerQuery
import com.uramnoil.serverist.application.server.queries.OrderBy
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindServersByOwnerQuery : FindServersByOwnerQuery {
    override suspend fun execute(ownerId: Uuid, limit: Int, offset: Long, sort: Sort, orderBy: OrderBy): List<Server> =
        newSuspendedTransaction {
            Servers.select { Servers.owner eq ownerId }.orderBy(
                when (orderBy) {
                    OrderBy.CreatedAt -> Servers.createdAt
                },
                when (sort) {
                    Sort.Asc -> SortOrder.ASC
                    Sort.Desc -> SortOrder.DESC
                }
            ).limit(limit, offset = offset).map(ResultRow::toApplicationServer)
        }
}