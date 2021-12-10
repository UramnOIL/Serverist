package com.uramnoil.serverist.serverist.infrastructure.application.server.queries

import com.uramnoil.serverist.serverist.infrastructure.Servers
import com.uramnoil.serverist.serverist.infrastructure.toApplicationServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*
import kotlin.coroutines.CoroutineContext

class FindServerByIdQueryUseCaseInteractor(
    private val outputPort: _root_ide_package_.com.uramnoil.serverist.serverist.application.server.queries.FindServerByIdQueryUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : _root_ide_package_.com.uramnoil.serverist.serverist.application.server.queries.FindServerByIdQueryUseCaseInputPort {
    override fun execute(id: UUID) {
        CoroutineScope(coroutineContext).launch {
            val row = kotlin.runCatching {
                newSuspendedTransaction {
                    Servers.select { Servers.id eq id }.firstOrNull()
                }
            }
            outputPort.handle(row.map { it?.toApplicationServer() })
            return@launch
        }
    }
}