package com.uramnoil.serverist.application.auth.unauthenticated.commands

import com.benasher44.uuid.Uuid

interface DeleteUserCommandInputPort {
    suspend fun execute(id: Uuid): Result<Unit>
}
