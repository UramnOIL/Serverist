package com.uramnoil.serverist.application.user.commands

import com.uramnoil.serverist.application.user.User

actual interface CreateUserCommand {
    fun execute(
        accountId: String,
        email: String,
        hashedPassword: String,
        name: String,
        description: String
    ): User
}

interface CreateUserCommandOutputPort {
    fun handle(result: Result<User>)
}
