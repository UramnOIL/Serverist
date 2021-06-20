package com.uramnoil.serverist.application.user.commands

import java.util.*

interface DeleteUserCommand {
    suspend fun execute(id: UUID)
}
