package com.uramnoil.serverist.application.user.commands

import com.benasher44.uuid.Uuid

actual interface DeleteUserCommandInputPort {
    suspend fun execute(id: Uuid): Result<Unit>
}
