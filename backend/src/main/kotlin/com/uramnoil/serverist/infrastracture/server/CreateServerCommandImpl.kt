package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.server.commands.CreateServerCommandDto
import com.uramnoil.serverist.domain.server.services.CreateServerService
import com.uramnoil.serverist.domain.user.repositories.UserRepository

class CreateServerCommandImpl(
    private val repository: UserRepository,
    private val service: CreateServerService,
) : CreateServerCommand {
    override suspend fun execute(dto: CreateServerCommandDto): Server {
        val owner = repository.findById(com.uramnoil.serverist.domain.kernel.models.UserId(dto.ownerId))
            ?: throw IllegalArgumentException("id=${dto.ownerId}に一致するユーザは存在しません。")

        return service.new(
            com.uramnoil.serverist.domain.server.models.Name(dto.name), owner,
            com.uramnoil.serverist.domain.server.models.Address(dto.address),
            com.uramnoil.serverist.domain.server.models.Port(dto.port),
            com.uramnoil.serverist.domain.server.models.Description(dto.description)
        ).toApplication()
    }
}