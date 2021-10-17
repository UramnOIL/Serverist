package com.uramnoil.serverist.application.unauthenticateduser.commands

import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser

interface CreateUnauthenticatedUserCommandInputPort {
    fun execute(
        accountId: String,
        email: String,
        password: String
    )
}

interface CreateUnauthenticatedUserCommandOutputPort {
    fun handle(result: Result<UnauthenticatedUser>)
}