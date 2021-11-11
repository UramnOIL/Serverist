package com.uramnoil.serverist.serverist.application.server.queries

import com.uramnoil.serverist.serverist.application.server.Server
import java.util.*

interface FindServerByIdQueryUseCaseInputPort {
    suspend fun execute(id: UUID): Result<Server?>
}