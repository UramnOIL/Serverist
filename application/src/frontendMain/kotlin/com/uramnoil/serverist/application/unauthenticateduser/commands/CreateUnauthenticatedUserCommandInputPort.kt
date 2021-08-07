package com.uramnoil.serverist.application.unauthenticateduser.commands

import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser

actual interface CreateUnauthenticatedUserCommandInputPort {
    fun execute(
        accountId: String,
        email: String,
        password: String
    ): UnauthenticatedUser
}

interface CreateUnauthenticatedUserCommandOutputPort {
    fun handle(result: Result<UnauthenticatedUser>)
}