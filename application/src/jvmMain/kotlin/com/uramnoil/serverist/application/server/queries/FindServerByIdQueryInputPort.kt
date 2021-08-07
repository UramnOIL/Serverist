package com.uramnoil.serverist.application.server.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.Server

actual interface FindServerByIdQueryInputPort {
    suspend fun execute(id: Uuid): Result<Server?>
}