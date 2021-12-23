package com.uramnoil.serverist.presenter

import com.uramnoil.serverist.serverist.application.OrderBy
import com.uramnoil.serverist.serverist.application.Sort
import com.uramnoil.serverist.serverist.application.server.Server
import com.uramnoil.serverist.serverist.application.server.commands.CreateServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.commands.CreateServerCommandUseCaseOutputPort
import com.uramnoil.serverist.serverist.application.server.commands.DeleteServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.commands.DeleteServerCommandUseCaseOutputPort
import com.uramnoil.serverist.serverist.application.server.commands.UpdateServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.commands.UpdateServerCommandUseCaseOutputPort
import com.uramnoil.serverist.serverist.application.server.queries.FindAllServersQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.queries.FindAllServersQueryUseCaseOutputPort
import com.uramnoil.serverist.serverist.application.server.queries.FindServerByIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.queries.FindServerByIdQueryUseCaseOutputPort
import com.uramnoil.serverist.serverist.application.server.queries.FindServersByOwnerQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.queries.FindServersByOwnerQueryUseCaseOutputPort
import com.uramnoil.serverist.serverist.application.server.services.ServerService
import kotlinx.coroutines.currentCoroutineContext
import java.util.UUID
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class ServerController(
    private val service: ServerService,
    private val createServerCommandUserCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: CreateServerCommandUseCaseOutputPort) -> CreateServerCommandUseCaseInputPort,
    private val updateServerCommandUserCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: UpdateServerCommandUseCaseOutputPort) -> UpdateServerCommandUseCaseInputPort,
    private val deleteServerCommandUserCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: DeleteServerCommandUseCaseOutputPort) -> DeleteServerCommandUseCaseInputPort,
    private val findByOwnerServerQueryUserCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: FindServersByOwnerQueryUseCaseOutputPort) -> FindServersByOwnerQueryUseCaseInputPort,
    private val findAllServerQueryUserCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: FindAllServersQueryUseCaseOutputPort) -> FindAllServersQueryUseCaseInputPort,
    private val findByIdServerQueryUserCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: FindServerByIdQueryUseCaseOutputPort) -> FindServerByIdQueryUseCaseInputPort,
) {
    suspend fun checkUserIsOwnerOfServer(userId: UUID, serverId: UUID): Result<Boolean> {
        return service.checkUserIsOwnerOfServer(userId, serverId)
    }

    suspend fun createServer(
        ownerId: UUID,
        name: String,
        host: String?,
        port: UShort?,
        description: String
    ): Result<UUID> {
        val coroutineContext = currentCoroutineContext()
        return suspendCoroutine {
            val outputPort = CreateServerCommandUseCaseOutputPort { result ->
                it.resume(result)
            }
            createServerCommandUserCaseInputPortFactory(coroutineContext, outputPort).execute(
                ownerId,
                name,
                host,
                port,
                description
            )
        }
    }

    suspend fun updateServer(
        id: UUID,
        name: String,
        address: String?,
        port: UShort?,
        description: String
    ): Result<Unit> {

        val coroutineContext = currentCoroutineContext()
        return suspendCoroutine {
            val outputPort = UpdateServerCommandUseCaseOutputPort { result ->
                it.resume(result)
            }
            updateServerCommandUserCaseInputPortFactory(coroutineContext, outputPort).execute(
                id,
                name,
                address,
                port,
                description
            )
        }
    }

    suspend fun deleteServer(
        uuid: UUID
    ): Result<Unit> {

        val coroutineContext = currentCoroutineContext()
        return suspendCoroutine {
            val outputPort = DeleteServerCommandUseCaseOutputPort { result ->
                it.resume(result)
            }
            deleteServerCommandUserCaseInputPortFactory(coroutineContext, outputPort).execute(uuid)
        }
    }

    suspend fun findServerByOwner(
        ownerId: UUID,
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ): Result<List<Server>> {

        val coroutineContext = currentCoroutineContext()
        return suspendCoroutine {
            val outputPort = FindServersByOwnerQueryUseCaseOutputPort { result ->
                it.resume(result)
            }
            findByOwnerServerQueryUserCaseInputPortFactory(coroutineContext, outputPort).execute(
                ownerId,
                limit,
                offset,
                sort,
                orderBy
            )
        }
    }

    suspend fun findAllServers(
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ): Result<List<Server>> {

        val coroutineContext = currentCoroutineContext()
        return suspendCoroutine {
            val outputPort = FindAllServersQueryUseCaseOutputPort { result ->
                it.resume(result)
            }
            findAllServerQueryUserCaseInputPortFactory(coroutineContext, outputPort).execute(
                limit,
                offset,
                sort,
                orderBy
            )
        }
    }

    suspend fun findServerById(
        id: UUID
    ): Result<Server?> {
        val coroutineContext = currentCoroutineContext()
        return suspendCoroutine {
            val outputPort = FindServerByIdQueryUseCaseOutputPort { result ->
                it.resume(result)
            }
            findByIdServerQueryUserCaseInputPortFactory(coroutineContext, outputPort).execute(id)
        }
    }
}