package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.commands.CreateServerCommand
import com.uramnoil.serverist.domain.server.models.Address
import com.uramnoil.serverist.domain.server.models.Description
import com.uramnoil.serverist.domain.server.models.Name
import com.uramnoil.serverist.domain.server.models.Port
import com.uramnoil.serverist.domain.server.services.CreateServerService
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import java.util.*

class CreateServerCommandImpl(
    private val repository: UserRepository,
    private val service: CreateServerService,
) : CreateServerCommand {
    override suspend fun execute(
        ownerId: UUID,
        name: String,
        address: String?,
        port: Int?,
        description: String
    ): Server {
        val owner = repository.findById(com.uramnoil.serverist.domain.kernel.models.UserId(ownerId))
            ?: throw IllegalArgumentException("id=${ownerId}に一致するユーザは存在しません。")

        return service.new(
            Name(name),
            owner,
            Address(address),
            Port(port),
            Description(description)
        ).toApplication()
    }
}