package com.uramnoil.serverist.serverist.infrastructures.application.usecase.server.queries

import com.uramnoil.serverist.serverist.application.usecases.OrderBy
import com.uramnoil.serverist.serverist.application.usecases.OrderBy.*
import com.uramnoil.serverist.serverist.application.usecases.Sort
import com.uramnoil.serverist.serverist.application.usecases.server.queries.FindServersByOwnerQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.usecases.server.queries.FindServersByOwnerQueryUseCaseOutputPort
import com.uramnoil.serverist.serverist.application.usecases.server.queries.ServerDto
import com.uramnoil.serverist.serverist.infrastructures.application.Servers
import com.uramnoil.serverist.serverist.infrastructures.application.usecase.server.toApplicationServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class ExposedFindServersByOwnerQueryInteractor(
) : FindServersByOwnerQueryUseCaseInputPort {
    override suspend fun execute(
        ownerId: UUID,
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ): Result<List<ServerDto>> {
        val row = newSuspendedTransaction {
            Servers.select { Servers.ownerId eq ownerId }.orderBy(
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
        return Result.success(row.map { it.toApplicationServer() })

    }
}
