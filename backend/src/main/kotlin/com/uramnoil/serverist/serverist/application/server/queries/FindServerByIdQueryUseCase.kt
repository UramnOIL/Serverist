package com.uramnoil.serverist.serverist.application.server.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.serverist.application.server.Server

/**
 *
 */
interface FindServerByIdQueryUseCaseInputPort {
    /**
     *
     */
    fun execute(id: Uuid)
}

/**
 *
 */
fun interface FindServerByIdQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Server?>)
}