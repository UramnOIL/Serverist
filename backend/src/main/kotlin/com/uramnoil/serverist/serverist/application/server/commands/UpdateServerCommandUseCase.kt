package com.uramnoil.serverist.serverist.application.server.commands

import java.util.*


interface UpdateServerCommandUseCaseInputPort {
    suspend fun execute(
        id: UUID,
        name: String,
        host: String?,
        port: UShort?,
        description: String
    ): Result<Unit>
}