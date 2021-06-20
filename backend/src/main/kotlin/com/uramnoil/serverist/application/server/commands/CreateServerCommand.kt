package com.uramnoil.serverist.application.server.commands

import com.uramnoil.serverist.application.server.Server
import java.util.*

interface CreateServerCommand {
    suspend fun execute(
        ownerId: UUID,
        name: String,
        address: String?,
        port: Int?,
        description: String
    ): Server
}