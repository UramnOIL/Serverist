package com.uramnoil.serverist.server.infrastructure.application.queries

import com.uramnoil.serverist.Sort
import com.uramnoil.serverist.server.application.Server
import com.uramnoil.serverist.server.application.queries.FindAllServersQueryUseCaseInputPort
import com.uramnoil.serverist.server.application.queries.OrderBy
import com.uramnoil.serverist.server.infrastructure.Servers
import com.uramnoil.serverist.server.infrastructure.toApplicationServer
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindAllServersQueryUseCaseInteractor : FindAllServersQueryUseCaseInputPort {
    override suspend fun execute(
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ): Result<List<Server>> {
        val result = kotlin.runCatching {
            val rows = newSuspendedTransaction {
                Servers.selectAll().orderBy(
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