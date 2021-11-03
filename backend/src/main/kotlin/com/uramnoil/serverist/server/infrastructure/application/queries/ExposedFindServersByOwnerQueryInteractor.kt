package com.uramnoil.serverist.server.infrastructure.application.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.Sort
import com.uramnoil.serverist.server.application.Server
import com.uramnoil.serverist.server.application.queries.FindServersByOwnerQueryUseCaseInputPort
import com.uramnoil.serverist.server.application.queries.OrderBy
import com.uramnoil.serverist.server.infrastructure.Servers
import com.uramnoil.serverist.server.infrastructure.toApplicationServer
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindServersByOwnerQueryInteractor : FindServersByOwnerQueryUseCaseInputPort {
    override suspend fun execute(
        ownerId: Uuid,
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ): Result<List<Server>> {
        val result = kotlin.runCatching {
            val rows = newSuspendedTransaction {
                Servers.select { Servers.owner eq ownerId }.orderBy(
                    when (orderBy) {
                        OrderBy.CreatedAt -> Servers.createdAt
                    },
                    when (sort) {
                        Sort.Asc -> SortOrder.ASC
                        Sort.Desc -> SortOrder.DESC
                    }
                ).limit(limit, offset = offset)
            }
            rows.map(ResultRow::toApplicationServer)
        }
        return result
    }
}