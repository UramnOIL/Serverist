package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.queries.FindAllServersQueryInputPort
import com.uramnoil.serverist.application.server.queries.OrderBy
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindAllServersQuery : FindAllServersQueryInputPort {
    override suspend fun execute(
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ): Result<List<Server>> = kotlin.runCatching {
        newSuspendedTransaction {
            Servers.selectAll().orderBy(
                when (orderBy) {
                    OrderBy.CreatedAt -> Servers.createdAt
                },
                when (sort) {
                    Sort.Asc -> SortOrder.ASC
                    Sort.Desc -> SortOrder.DESC
                }
            ).limit(limit, offset = offset)
        }.map(ResultRow::toApplicationServer)
    }
}