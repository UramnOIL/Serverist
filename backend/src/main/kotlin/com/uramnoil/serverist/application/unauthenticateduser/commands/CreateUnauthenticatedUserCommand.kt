package com.uramnoil.serverist.application.unauthenticateduser.commands

import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser

interface CreateUnauthenticatedUserCommand {
    suspend fun execute(
        accountId: String,
        email: String,
        hashedPassword: String
    ): UnauthenticatedUser
}