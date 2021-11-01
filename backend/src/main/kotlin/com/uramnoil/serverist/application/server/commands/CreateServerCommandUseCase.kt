package com.uramnoil.serverist.application.server.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.Server

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
        ownerId: Uuid,
        name: String,
        host: String?,
        port: Int?,
        description: String
    ): Result<Server>
}