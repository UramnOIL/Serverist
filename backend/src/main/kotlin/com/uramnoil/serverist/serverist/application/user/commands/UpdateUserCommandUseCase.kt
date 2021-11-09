package com.uramnoil.serverist.serverist.application.user.commands

import com.benasher44.uuid.Uuid

interface UpdateUserCommandUseCaseInputPort {
    suspend fun execute(id: Uuid, accountId: String, name: String, description: String): Result<Unit>
}