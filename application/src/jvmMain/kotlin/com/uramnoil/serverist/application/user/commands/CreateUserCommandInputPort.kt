package com.uramnoil.serverist.application.user.commands

import com.uramnoil.serverist.application.user.User

actual interface CreateUserCommandInputPort {
    suspend fun execute(
        accountId: String,
        email: String,
        hashedPassword: String,
        name: String,
        description: String
    ): Result<User>
}
