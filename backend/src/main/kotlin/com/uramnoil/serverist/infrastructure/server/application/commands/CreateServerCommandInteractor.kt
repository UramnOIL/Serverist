package com.uramnoil.serverist.infrastructure.server.application.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.commands.CreateServerCommandUseCaseInputPort
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.server.models.*
import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.infrastracture.server.toApplication
import kotlinx.datetime.Clock
import com.uramnoil.serverist.domain.server.models.Server as DomainServer

class CreateServerCommandInteractor(
    private val userRepository: UserRepository,
    private val serverRepository: ServerRepository,
) : CreateServerCommandUseCaseInputPort {
    override suspend fun execute(
        ownerId: Uuid,
        name: String,
        host: String?,
        port: Int?,
        description: String
    ): Result<Server> {
        val findByIdResult = userRepository.findById(Id(ownerId))

        val result = findByIdResult.mapCatching {
            it ?: throw IllegalArgumentException("id: ${ownerId}のユーザー見つかりませんでした。")

            val server = DomainServer(
                Id(Uuid.randomUUID()),
                CreatedAt(Clock.System.now()),
                Name(name),
                it.id,
                Address(host),
                Port(port),
                Description(description)
            )

            serverRepository.insert(server)
            server.toApplication()
        }

        return result
    }
}