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
    fun execute(id: UUID)
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