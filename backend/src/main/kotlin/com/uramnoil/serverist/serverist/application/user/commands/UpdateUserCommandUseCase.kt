package com.uramnoil.serverist.serverist.application.user.commands

import java.util.*

interface UpdateUserCommandUseCaseInputPort {
    suspend fun execute(id: UUID, accountId: String, name: String, description: String): Result<Unit>
}