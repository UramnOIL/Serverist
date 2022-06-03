package com.uramnoil.serverist.serverist.application.usecases.server.queries

import java.util.UUID

/**
 *
 */
interface FindServerByIdQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(id: UUID): Result<ServerDto?>
}

