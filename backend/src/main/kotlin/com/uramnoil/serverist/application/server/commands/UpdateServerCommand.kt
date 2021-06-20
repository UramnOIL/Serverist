package com.uramnoil.serverist.application.server.commands

import java.util.*


interface UpdateServerCommand {
    suspend fun execute(
        id: UUID,
        name: String,
        address: String?,
        port: Int?,
        description: String
    )
}