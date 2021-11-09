package com.uramnoil.serverist.serverist.application.server.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.serverist.application.server.Server

interface FindServerByIdQueryUseCaseInputPort {
    suspend fun execute(id: Uuid): Result<Server?>
}