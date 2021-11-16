package com.uramnoil.serverist.serverist.application.server.commands

import java.util.*

/**
 * A input-port of CreateServerCommandUseCase.
 */
/**
 *
 */
interface CreateServerCommandUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(ownerId: UUID, name: String, host: String?, port: UShort?, description: String)
}

interface CreateServerCommandUseCaseOutputPort {
    /**
     *
     */
    suspend fun handle(result: Result<UUID>)
}