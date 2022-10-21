package com.uramnoil.serverist.infrastracture.server.application.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.queries.FindServersByOwnerQueryUseCaseInputPort
import com.uramnoil.serverist.application.server.queries.FindServersByOwnerQueryUseCaseOutputPort
import com.uramnoil.serverist.application.server.queries.OrderBy
import com.uramnoil.serverist.infrastracture.server.Servers
import com.uramnoil.serverist.infrastracture.server.toApplicationServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindServersByOwnerQueryInteractor(
    private val outputPort: FindServersByOwnerQueryUseCaseOutputPort,
    parentContext: CoroutineContext
) :
    FindServersByOwnerQueryUseCaseInputPort,
    CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(
        ownerId: Uuid,
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ) {
        launch {
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
            outputPort.handle(result)
        }
    }
}