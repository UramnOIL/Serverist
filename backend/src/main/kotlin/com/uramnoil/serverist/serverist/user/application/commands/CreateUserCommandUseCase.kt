package com.uramnoil.serverist.serverist.user.application.commands

import java.util.*

interface CreateUserCommandUseCaseInputPort {
    suspend fun execute(
        id: UUID,
        accountId: String,
        name: String,
        description: String
    ): Result<UUID>
}