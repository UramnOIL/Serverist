package com.uramnoil.serverist.application.user.commands

import com.uramnoil.serverist.application.user.User

interface CreateUserCommandInputPort {
    fun execute(
        accountId: String,
        email: String,
        hashedPassword: String,
        name: String,
        description: String
    )
}

interface CreateUserCommandOutputPort {
    fun handle(result: Result<User>)
}
