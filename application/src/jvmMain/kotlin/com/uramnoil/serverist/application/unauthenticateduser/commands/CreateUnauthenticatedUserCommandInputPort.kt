package com.uramnoil.serverist.application.unauthenticateduser.commands

import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser

actual interface CreateUnauthenticatedUserCommandInputPort {
    suspend fun execute(
        accountId: String,
        email: String,
        password: String
    ): Result<UnauthenticatedUser>
}