package com.uramnoil.serverist.serverist.application.server.queries

import com.uramnoil.serverist.serverist.application.server.Server
import java.util.*

/**
 *
 */
interface FindServerByIdQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(id: UUID)
}

/**
 *
 */
fun interface FindServerByIdQueryUseCaseOutputPort {
    /**
     *
     */
    suspend fun handle(result: Result<Server?>)
}