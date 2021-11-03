package com.uramnoil.serverist.serverist.server.application.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.serverist.server.application.Server

interface FindServerByIdQueryUseCaseInputPort {
    suspend fun execute(id: Uuid): Result<Server?>
}