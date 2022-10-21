package com.uramnoil.serverist.infrastracture.server.application.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.queries.FindServerByIdQueryUseCaseInputPort
import com.uramnoil.serverist.application.server.queries.FindServerByIdQueryUseCaseOutputPort
import com.uramnoil.serverist.infrastracture.server.Servers
import com.uramnoil.serverist.infrastracture.server.toApplicationServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class FindServerByIdQueryUseCaseInteractor(
    private val outputPort: FindServerByIdQueryUseCaseOutputPort,
    parentContext: CoroutineContext
) :
    FindServerByIdQueryUseCaseInputPort,
    CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(id: Uuid) {
        launch {
            val row = kotlin.runCatching {
                newSuspendedTransaction {
                    Servers.select { Servers.id eq id }.firstOrNull()
                }
            }
            val result = row.map { it?.toApplicationServer() }
            outputPort.handle(result)
        }
    }
}