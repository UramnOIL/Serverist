package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.server.commands.CreateServerCommandDto
import com.uramnoil.serverist.domain.models.kernel.models.UserId
import com.uramnoil.serverist.domain.models.server.models.Address
import com.uramnoil.serverist.domain.models.server.models.Description
import com.uramnoil.serverist.domain.models.server.models.Name
import com.uramnoil.serverist.domain.models.server.models.Port
import com.uramnoil.serverist.domain.models.server.services.CreateServerService
import com.uramnoil.serverist.domain.models.user.repositories.UserRepository

class CreateServerCommandImpl(
    private val repository: UserRepository,
    private val service: CreateServerService,
) : CreateServerCommand {
    override suspend fun execute(dto: CreateServerCommandDto): Server {
        val owner = repository.findById(UserId(dto.ownerId))
            ?: throw IllegalArgumentException("id=${dto.ownerId}に一致するユーザは存在しません。")

        return service.new(Name(dto.name), owner, Address(dto.address), Port(dto.port), Description(dto.description))
            .toApplicationServer()
    }
}

fun com.uramnoil.serverist.domain.models.server.models.Server.toApplicationServer() = Server(
    id = id.value,
    createdAt = createdAt.value,
    ownerId = ownerId.value,
    name = name.value,
    address = address.value,
    port = port.value,
    description = description.value
)