package com.uramnoil.serverist.serverist.infrastructure.application.server.commands

import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.models.server.CreatedAt
import com.uramnoil.serverist.domain.serverist.models.server.Description
import com.uramnoil.serverist.domain.serverist.models.server.Host
import com.uramnoil.serverist.domain.serverist.models.server.Name
import com.uramnoil.serverist.domain.serverist.models.server.Port
import com.uramnoil.serverist.domain.serverist.repositories.ServerRepository
import com.uramnoil.serverist.domain.serverist.repositories.UserRepository
import com.uramnoil.serverist.serverist.application.server.Server
import com.uramnoil.serverist.serverist.application.server.commands.CreateServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.commands.CreateServerCommandUseCaseOutputPort
import com.uramnoil.serverist.serverist.infrastructure.toApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.util.UUID
import kotlin.coroutines.CoroutineContext
import com.uramnoil.serverist.domain.serverist.models.server.Server as DomainServer

class CreateServerCommandInteractor(
    private val userRepository: UserRepository,
    private val serverRepository: ServerRepository,
    private val outputPort: CreateServerCommandUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : CreateServerCommandUseCaseInputPort {
    override fun execute(
        ownerId: UUID,
        name: String,
        host: String?,
        port: UShort?,
        description: String
    ) {
        CoroutineScope(coroutineContext).launch {
            val findByIdResult = userRepository.findById(Id(ownerId))

            val result: Result<Server> = findByIdResult.mapCatching { user ->
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

            outputPort.handle(result.map { it.id })
            return@launch
        }
    }
}
