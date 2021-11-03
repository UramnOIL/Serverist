package com.uramnoil.serverist.auth.application.unauthenticated.commands

import com.benasher44.uuid.Uuid

interface DeleteUserCommandInputPort {
    suspend fun execute(id: Uuid): Result<Unit>
}
