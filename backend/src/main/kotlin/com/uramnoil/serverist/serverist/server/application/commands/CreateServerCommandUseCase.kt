package com.uramnoil.serverist.serverist.server.application.commands

import java.util.*

/**
 * A input-port of CreateServerCommandUseCase.
 */
interface CreateServerCommandUseCaseInputPort {
    /**
     * Request to create a new server.
     *
     * @param ownerId       server owner's UUID
     * @param name          the name of the new server
     * @param host          the host of the new server
     * @param port          the port of the new server
     * @param description   the description of the new server
     */
    suspend fun execute(
        ownerId: UUID,
        name: String,
        host: String?,
        port: Int?,
        description: String
    ): Result<UUID>
}