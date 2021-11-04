package com.uramnoil.serverist.presenter

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.Sort
import com.uramnoil.serverist.serverist.server.application.Server
import com.uramnoil.serverist.serverist.server.application.commands.CreateServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.server.application.commands.DeleteServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.server.application.commands.UpdateServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.server.application.queries.FindAllServersQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.server.application.queries.FindServerByIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.server.application.queries.FindServersByOwnerQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.server.application.queries.OrderBy
import com.uramnoil.serverist.serverist.server.application.services.ServerService
import java.util.*


class ServerController(
    private val service: ServerService,
    private val createCommand: CreateServerCommandUseCaseInputPort,
    private val updateCommand: UpdateServerCommandUseCaseInputPort,
    private val deleteCommand: DeleteServerCommandUseCaseInputPort,
    private val findByOwnerQuery: FindServersByOwnerQueryUseCaseInputPort,
    private val findAllQuery: FindAllServersQueryUseCaseInputPort,
    private val findByIdQuery: FindServerByIdQueryUseCaseInputPort,
) {
    suspend fun checkUserIsOwnerOfServer(userId: Uuid, serverId: Uuid): Result<Boolean> {
        return service.checkUserIsOwnerOfServer(userId, serverId)
    }

    suspend fun createServer(
        ownerId: UUID,
        name: String,
        host: String?,
        port: Int?,
        description: String
    ): Result<UUID> {
        return createCommand.execute(ownerId, name, host, port, description)
    }

    suspend fun updateServer(
        id: UUID,
        name: String,
        address: String?,
        port: Int?,
        description: String
    ): Result<Unit> {
        return updateCommand.execute(
            id,
            name,
            address,
            port,
            description
        )
    }

    suspend fun deleteServer(
        uuid: UUID
    ): Result<Unit> {
        return deleteCommand.execute(uuid)
    }

    suspend fun findServerByOwner(
        ownerId: Uuid,
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ): Result<List<Server>> {
        return findByOwnerQuery.execute(ownerId, limit, offset, sort, orderBy)
    }

    suspend fun findAllServers(
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ): Result<List<Server>> {
        return findAllQuery.execute(limit, offset, sort, orderBy)
    }

    suspend fun findServerByIdQueryUseCase(
        id: UUID
    ): Result<Server?> {
        return findByIdQuery.execute(id)
    }
}