package com.uramnoil.serverist.serverist.infrastructure.application.server.queries

import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.serverist.application.server.queries.FindServersByOwnerQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.queries.FindServersByOwnerQueryUseCaseOutputPort
import com.uramnoil.serverist.serverist.infrastructure.Servers
import com.uramnoil.serverist.serverist.infrastructure.toApplicationServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class ExposedFindServersByOwnerQueryInteractor(
    private val outputPort: FindServersByOwnerQueryUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : FindServersByOwnerQueryUseCaseInputPort {
    override fun execute(
        ownerId: UUID,
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ) {
        kotlin.runCatching {
            CoroutineScope(coroutineContext).launch {
                val row = newSuspendedTransaction {
                    Servers.select { Servers.ownerId eq ownerId }.orderBy(
                        when (orderBy) {
                            OrderBy.CreatedAt -> Servers.createdAt
                        },
                        when (sort) {
                            Sort.Asc -> SortOrder.ASC
                            Sort.Desc -> SortOrder.DESC
                        }
                    ).limit(limit, offset = offset).toList()
                }
                outputPort.handle(Result.success(row.map { it.toApplicationServer() }))
            }
        }
    }
}
