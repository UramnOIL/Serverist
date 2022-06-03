package com.uramnoil.serverist.serverist.infrastructures.application.usecase.server.commands

import com.uramnoil.serverist.serverist.domain.models.server.Id
import com.uramnoil.serverist.serverist.domain.models.server.DateTime
import com.uramnoil.serverist.serverist.domain.models.server.Host
import com.uramnoil.serverist.serverist.domain.models.server.Name
import com.uramnoil.serverist.serverist.domain.models.server.Port
import com.uramnoil.serverist.serverist.domain.models.server.Description
import com.uramnoil.serverist.common.domain.models.kernel.UserId
import com.uramnoil.serverist.serverist.domain.repositories.ServerRepository
import com.uramnoil.serverist.serverist.domain.repositories.UserRepository
import com.uramnoil.serverist.serverist.application.usecases.server.commands.CreateServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.usecases.server.commands.CreateServerCommandUseCaseOutputPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.util.UUID
import kotlin.coroutines.CoroutineContext
import com.uramnoil.serverist.serverist.domain.models.server.Server as DomainServer

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
        val server = DomainServer(
            Id(UUID.randomUUID()),
            DateTime(Clock.System.now()),
            Name(name),
            UserId(ownerId),
            host?.let { Host(it) },
            port?.let { Port(it) },
            Description(description)
        )

        serverRepository.insert(server).onFailure {
            return Result.failure(it)
        }

        return Result.success(server.id.value)
    }
}
