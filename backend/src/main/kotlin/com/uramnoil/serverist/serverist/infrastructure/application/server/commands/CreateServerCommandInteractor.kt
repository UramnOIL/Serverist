package com.uramnoil.serverist.serverist.infrastructure.application.server.commands

import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.models.server.*
import com.uramnoil.serverist.domain.serverist.repositories.ServerRepository
import com.uramnoil.serverist.domain.serverist.repositories.UserRepository
import com.uramnoil.serverist.serverist.application.server.commands.CreateServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.infrastructure.application.server.toApplication
import kotlinx.datetime.Clock
import java.util.*
import com.uramnoil.serverist.domain.serverist.models.server.Server as DomainServer

class CreateServerCommandInteractor(
    private val userRepository: UserRepository,
    private val serverRepository: ServerRepository,
) : CreateServerCommandUseCaseInputPort {
    override suspend fun execute(
        ownerId: UUID,
        name: String,
        host: String?,
        port: UShort?,
        description: String
    ): Result<UUID> {
        val findByIdResult = userRepository.findById(Id(ownerId))

        val result = findByIdResult.mapCatching { user ->
            user ?: throw IllegalArgumentException("id: ${ownerId}のユーザー見つかりませんでした。")

            val server = DomainServer(
                Id(UUID.randomUUID()),
                CreatedAt(Clock.System.now()),
                Name(name),
                user.id,
                host?.let { Host(it) },
                port?.let { Port(it) },
                Description(description)
            )

            serverRepository.insert(server)
            server.toApplication()
        }

        return result.map { it.id }
    }
}