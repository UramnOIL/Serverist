package com.uramnoil.serverist.application.user.commands

import com.benasher44.uuid.Uuid

interface DeleteUserCommandUseCaseInputPort {
    suspend fun execute(id: Uuid): Result<Unit>
}