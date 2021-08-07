package com.uramnoil.serverist.application.server.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.Server

actual interface CreateServerCommandInputPort {
    suspend fun execute(
        ownerId: Uuid,
        name: String,
        address: String?,
        port: Int?,
        description: String
    ): Result<Server>
}