package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.commands.CreateServerCommand
import com.uramnoil.serverist.domain.kernel.models.user.UserId
import com.uramnoil.serverist.domain.server.models.*
import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import kotlinx.datetime.Clock
import java.util.*
import com.uramnoil.serverist.application.server.Server as ApplicationServer
import com.uramnoil.serverist.domain.server.models.Server as DomainServer

class CreateServerCommandImpl(
    private val userRepository: UserRepository,
    private val serverRepository: ServerRepository,
) : CreateServerCommand {
    override suspend fun execute(
        ownerId: UUID,
        name: String,
        address: String?,
        port: Int?,
        description: String
    ): ApplicationServer {
        val owner = userRepository.findById(UserId(ownerId))
            ?: throw IllegalArgumentException("id=${ownerId}に一致するユーザは存在しません。")

        val server = DomainServer(
            Id(UUID.randomUUID()),
            CreatedAt(Clock.System.now()),
            Name(name),
            owner.id,
            Address(address),
            Port(port),
            Description(description)
        )

        serverRepository.insert(server)

        return server.toApplication()
    }
}