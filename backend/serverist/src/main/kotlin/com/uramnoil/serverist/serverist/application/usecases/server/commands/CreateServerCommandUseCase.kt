package com.uramnoil.serverist.serverist.application.usecases.server.commands

import java.util.UUID

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
    suspend fun execute(ownerId: UUID, name: String, host: String?, port: UShort?, description: String): Result<UUID>
}

