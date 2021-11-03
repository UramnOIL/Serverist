package com.uramnoil.serverist.server.application.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.server.application.Server

interface FindServerByIdQueryUseCaseInputPort {
    suspend fun execute(id: Uuid): Result<Server?>
}