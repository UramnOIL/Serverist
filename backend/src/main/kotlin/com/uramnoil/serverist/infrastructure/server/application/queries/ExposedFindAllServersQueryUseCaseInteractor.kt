package com.uramnoil.serverist.infrastracture.server.application.queries

import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.queries.FindAllServersQueryUseCaseInputPort
import com.uramnoil.serverist.application.server.queries.FindAllServersQueryUseCaseOutputPort
import com.uramnoil.serverist.application.server.queries.OrderBy
import com.uramnoil.serverist.infrastracture.server.Servers
import com.uramnoil.serverist.infrastracture.server.toApplicationServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindAllServersQueryUseCaseInteractor(
    private val outputPort: FindAllServersQueryUseCaseOutputPort,
    parentContext: CoroutineContext
) :
    FindAllServersQueryUseCaseInputPort,
    CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ) {
        launch {
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
            outputPort.handle(result)
        }
    }
}