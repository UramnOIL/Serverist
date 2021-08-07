package com.uramnoil.serverist.infrastracture.server

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.commands.CreateServerCommandInputPort
import com.uramnoil.serverist.domain.kernel.models.user.UserId
import com.uramnoil.serverist.domain.server.models.*
import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import kotlinx.datetime.Clock
import com.uramnoil.serverist.application.server.Server as ApplicationServer
import com.uramnoil.serverist.domain.server.models.Server as DomainServer

class CreateServerCommandInteractor(
    private val userRepository: UserRepository,
    private val serverRepository: ServerRepository,
) : CreateServerCommandInputPort {
    override suspend fun execute(
        ownerId: Uuid,
        name: String,
        address: String?,
        port: Int?,
        description: String
    ): Result<ApplicationServer> = userRepository.findById(UserId(ownerId)).map {
        it ?: return Result.failure(IllegalArgumentException("id: ${ownerId}のユーザー見つかりませんでした。"))

        val server = DomainServer(
            Id(Uuid.randomUUID()),
            CreatedAt(Clock.System.now()),
            Name(name),
            it.id,
            Address(address),
            Port(port),
            Description(description)
        )

        serverRepository.insert(server)
        server.toApplication()
    }
}