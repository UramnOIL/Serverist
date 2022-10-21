package com.uramnoil.serverist.infrastructure.server.application.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.commands.CreateServerCommandUseCaseInputPort
import com.uramnoil.serverist.application.server.commands.CreateServerCommandUseCaseOutputPort
import com.uramnoil.serverist.domain.common.user.UserId
import com.uramnoil.serverist.domain.server.models.*
import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.infrastracture.server.toApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.coroutines.CoroutineContext
import com.uramnoil.serverist.domain.server.models.Server as DomainServer

class CreateServerCommandInteractor(
    private val outputPort: CreateServerCommandUseCaseOutputPort,
    private val userRepository: UserRepository,
    private val serverRepository: ServerRepository,
    parentContext: CoroutineContext
) : CreateServerCommandUseCaseInputPort, CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(
        ownerId: Uuid,
        name: String,
        host: String?,
        port: Int?,
        description: String
    ) {
        launch {
            val findByIdResult = userRepository.findById(UserId(ownerId))

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

            outputPort.handle(result)
        }
    }
}