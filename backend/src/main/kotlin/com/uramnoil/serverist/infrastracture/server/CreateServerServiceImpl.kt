package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.domain.models.server.*
import com.uramnoil.serverist.domain.models.user.User
import com.uramnoil.serverist.domain.repositories.ServerRepository
import com.uramnoil.serverist.domain.services.server.CreateServerService
import kotlinx.datetime.Clock
import java.util.*

class CreateServerServiceImpl(private val repository: ServerRepository) :
    CreateServerService {
    override suspend fun new(
        name: Name,
        owner: User,
        address: Address,
        port: Port,
        description: Description
    ): Server {
        val server = Server(
            id = Id(UUID.randomUUID()),
            createdAt = CreatedAt(Clock.System.now()),
            ownerId = owner.id,
            name = name,
            address = address,
            port = port,
            description = description
        )
        repository.insert(server)
        return server
    }
}