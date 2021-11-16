package com.uramnoil.serverist.serverist.application.server.commands

import java.util.*

/**
 *
 */
interface DeleteServerCommandUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(id: UUID)
}

interface DeleteServerCommandUseCaseOutputPort {
    /**
     *
     */
    suspend fun handle(result: Result<Unit>)
}