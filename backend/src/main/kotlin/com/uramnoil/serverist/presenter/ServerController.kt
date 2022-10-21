package com.uramnoil.serverist.presenter

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.commands.*
import com.uramnoil.serverist.application.server.queries.*
import com.uramnoil.serverist.application.server.services.ServerService
import org.kodein.di.DI
import org.kodein.di.instance
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class ServerController(
    private val di: DI
) {
    suspend fun checkUserIsOwnerOfServer(userId: Uuid, serverId: Uuid): Result<Boolean> {
        val service by di.instance<ServerService>()
        return service.checkUserIsOwnerOfServer(userId, serverId)
    }

    suspend fun createServer(
        ownerId: UUID,
        name: String,
        host: String?,
        port: Int?,
        description: String
    ): Result<Server> = suspendCoroutine {
        val presenter = CreateServerCommandUseCaseOutputPort { result ->
            it.resume(result)
        }
        val command by di.instance<CreateServerCommandUseCaseInputPort>(presenter)
        command.execute(ownerId, name, host, port, description)
    }

    suspend fun updateServer(
        id: UUID,
        name: String,
        address: String?,
        port: Int?,
        description: String
    ): Result<Unit> = suspendCoroutine {
        val presenter = UpdateServerCommandUseCaseOutputPort { result ->
            it.resume(result)
        }
        val command by di.instance<UpdateServerCommandUseCaseInputPort>(presenter)
        command.execute(
            id,
            name,
            address,
            port,
            description
        )
    }

    suspend fun deleteServer(
        uuid: UUID
    ): Result<Unit> = suspendCoroutine {
        val presenter = DeleteServerCommandUseCaseOutputPort { result ->
            it.resume(result)
        }
        val command by di.instance<DeleteServerCommandUseCaseInputPort>(presenter)
        command.execute(uuid)
    }

    suspend fun findServerByOwner(
        ownerId: Uuid,
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ): Result<List<Server>> = suspendCoroutine {
        val presenter = FindServersByOwnerQueryUseCaseOutputPort { result ->
            it.resume(result)
        }
        val command by di.instance<FindServersByOwnerQueryUseCaseInputPort>(presenter)
        command.execute(ownerId, limit, offset, sort, orderBy)
    }

    suspend fun findAllServers(
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ): Result<List<Server>> = suspendCoroutine {
        val presenter = FindAllServersQueryUseCaseOutputPort { result ->
            it.resume(result)
        }
        val command by di.instance<FindAllServersQueryUseCaseInputPort>(presenter)
        command.execute(limit, offset, sort, orderBy)
    }

    suspend fun findServerByIdQueryUseCase(
        id: UUID
    ): Result<Server?> = suspendCoroutine {
        val presenter = FindServerByIdQueryUseCaseOutputPort { result ->
            it.resume(result)
        }
        val command by di.instance<FindServerByIdQueryUseCaseInputPort>(presenter)
        command.execute(id)
    }
}