package com.uramnoil.serverist.user.application.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.user.application.User

interface CreateUserCommandUseCaseInputPort {
    suspend fun execute(
        id: Uuid,
        accountId: String,
        name: String,
        description: String
    ): Result<User>
}