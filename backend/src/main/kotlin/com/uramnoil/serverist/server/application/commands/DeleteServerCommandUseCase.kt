package com.uramnoil.serverist.server.application.commands

import com.benasher44.uuid.Uuid

interface DeleteServerCommandUseCaseInputPort {
    suspend fun execute(id: Uuid): Result<Unit>
}