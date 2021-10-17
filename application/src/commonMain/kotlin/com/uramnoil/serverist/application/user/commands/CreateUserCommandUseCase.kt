package com.uramnoil.serverist.application.user.commands

import com.uramnoil.serverist.application.user.User

interface CreateUserCommandUseCaseInputPort {
    fun execute(
        accountId: String,
        email: String,
        hashedPassword: String,
        name: String,
        description: String
    )
}

interface CreateUserCommandUseCaseOutputPort {
    fun handle(result: Result<User>)
}
