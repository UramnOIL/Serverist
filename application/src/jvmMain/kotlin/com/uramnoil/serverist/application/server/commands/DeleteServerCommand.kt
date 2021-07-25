package com.uramnoil.serverist.application.server.commands

import com.benasher44.uuid.Uuid

actual interface DeleteServerCommand {
    suspend fun execute(id: Uuid): Result<Unit>
}
