package com.uramnoil.serverist.application.unauthenticateduser.commands

import com.benasher44.uuid.Uuid

actual interface DeleteUnauthenticatedUserCommandInputPort {
    suspend fun execute(id: Uuid): Result<Unit>
}