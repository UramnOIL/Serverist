package com.uramnoil.serverist.application.user.commands

interface CreateUserCommand {
    suspend fun execute(
        accountId: String,
        email: String,
        hashedPassword: String,
        name: String,
        description: String
    )
}
