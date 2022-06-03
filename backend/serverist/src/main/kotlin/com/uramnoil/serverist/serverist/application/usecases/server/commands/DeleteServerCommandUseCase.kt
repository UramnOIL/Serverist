package com.uramnoil.serverist.serverist.application.usecases.server.commands

import java.util.*

/**
 *
 */
interface DeleteServerCommandUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(id: UUID): Result<Unit>
}

