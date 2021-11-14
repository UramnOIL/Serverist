package com.uramnoil.serverist.presenter

import com.uramnoil.serverist.Sort
import com.uramnoil.serverist.serverist.application.server.Server
import com.uramnoil.serverist.serverist.application.server.commands.CreateServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.commands.DeleteServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.commands.UpdateServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.queries.FindAllServersQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.queries.FindServerByIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.queries.FindServersByOwnerQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.queries.OrderBy
import com.uramnoil.serverist.serverist.application.server.services.ServerService
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
        return createCommand.execute(ownerId, name, host, port, description)
    }

    suspend fun updateServer(
        id: UUID,
        name: String,
        address: String?,
        port: UShort?,
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
        ownerId: UUID,
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

    suspend fun findServerById(
        id: UUID
    ): Result<Server?> {
        return findByIdQuery.execute(id)
    }
}