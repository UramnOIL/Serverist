package com.uramnoil.serverist.application.unauthenticateduser.commands

import java.util.*

interface DeleteUnauthenticatedUserCommand {
    suspend fun execute(id: UUID)
}