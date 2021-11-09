package com.uramnoil.serverist.serverist.application.server.commands

import com.benasher44.uuid.Uuid

interface DeleteServerCommandUseCaseInputPort {
    suspend fun execute(id: Uuid): Result<Unit>
}