package com.uramnoil.serverist.application.auth.authenticated.commands

import com.benasher44.uuid.Uuid

interface DeleteUserCommandUseCaseInputPort {
    suspend fun execute(id: Uuid): Result<Unit>
}