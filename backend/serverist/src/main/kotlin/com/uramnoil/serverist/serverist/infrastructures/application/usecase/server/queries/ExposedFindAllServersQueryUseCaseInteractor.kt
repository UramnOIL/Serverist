package com.uramnoil.serverist.serverist.infrastructures.application.usecase.server.queries

import com.uramnoil.serverist.serverist.application.usecases.OrderBy
import com.uramnoil.serverist.serverist.application.usecases.OrderBy.*
import com.uramnoil.serverist.serverist.application.usecases.Sort
import com.uramnoil.serverist.serverist.application.usecases.server.queries.FindAllServersQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.usecases.server.queries.FindAllServersQueryUseCaseOutputPort
import com.uramnoil.serverist.serverist.application.usecases.server.queries.ServerDto
import com.uramnoil.serverist.serverist.application.usecases.server.queries.ServerWithOwnerDto
import com.uramnoil.serverist.serverist.infrastructures.application.Servers
import com.uramnoil.serverist.serverist.infrastructures.application.usecase.server.toApplicationServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindAllServersQueryUseCaseInteractor(


) : FindAllServersQueryUseCaseInputPort {
    override suspend fun execute(
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ): Result<List<ServerWithOwnerDto>> {
        return kotlin.runCatching {
            val rows = newSuspendedTransaction {
                Servers.selectAll().orderBy(
                    when (orderBy) {
                        CreatedAt -> Servers.createdAt
                        UpdatedAt -> TODO()
                    },
                    when (sort) {
                        Sort.Asc -> SortOrder.ASC
                        Sort.Desc -> SortOrder.DESC
                    }
                ).limit(limit, offset = offset).toList()
            }
            rows.map(ResultRow::toApplicationServer)
        }
    }
}
