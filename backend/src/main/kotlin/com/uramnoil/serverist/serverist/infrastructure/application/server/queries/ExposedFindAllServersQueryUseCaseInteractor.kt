package com.uramnoil.serverist.serverist.infrastructure.application.server.queries

import com.uramnoil.serverist.serverist.application.OrderBy
import com.uramnoil.serverist.serverist.application.Sort
import com.uramnoil.serverist.serverist.application.server.Server
import com.uramnoil.serverist.serverist.application.server.queries.FindAllServersQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.queries.FindAllServersQueryUseCaseOutputPort
import com.uramnoil.serverist.serverist.infrastructure.Servers
import com.uramnoil.serverist.serverist.infrastructure.toApplicationServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindAllServersQueryUseCaseInteractor(
    private val outputPort: FindAllServersQueryUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : FindAllServersQueryUseCaseInputPort {
    override fun execute(limit: Int, offset: Long, sort: Sort, orderBy: OrderBy) {
        CoroutineScope(coroutineContext).launch {
            val result: Result<List<Server>> = kotlin.runCatching {
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
            return@launch
        }
    }
}
