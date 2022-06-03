package com.uramnoil.serverist.serverist.infrastructures.application.usecase.server.queries

import com.uramnoil.serverist.serverist.application.usecases.server.queries.FindServerByIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.usecases.server.queries.FindServerByIdQueryUseCaseOutputPort
import com.uramnoil.serverist.serverist.application.usecases.server.queries.ServerDto
import com.uramnoil.serverist.serverist.infrastructures.application.Servers
import com.uramnoil.serverist.serverist.infrastructures.application.usecase.server.toApplicationServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class FindServerByIdQueryUseCaseInteractor(


) : FindServerByIdQueryUseCaseInputPort {
    override suspend fun execute(id: UUID): Result<ServerDto?> {
        val row = kotlin.runCatching {
            newSuspendedTransaction {
                Servers.select { Servers.id eq id }.firstOrNull()
            }
        }
        return row.map { it?.toApplicationServer() }
    }
}
