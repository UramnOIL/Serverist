package com.uramnoil.serverist.application.user.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.User

interface CreateUserCommandUseCaseInputPort {
    fun execute(
        id: Uuid,
        accountId: String,
        name: String,
        description: String
    )
}

fun interface CreateUserCommandUseCaseOutputPort {
    fun handle(result: Result<User>)
}
