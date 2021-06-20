package com.uramnoil.serverist.application.user.commands

import java.util.*

interface UpdateUserProfileCommand {
    suspend fun execute(id: UUID, accountId: String, name: String, description: String)
}
