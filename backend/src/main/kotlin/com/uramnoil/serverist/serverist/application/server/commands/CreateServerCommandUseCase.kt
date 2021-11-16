package com.uramnoil.serverist.serverist.application.server.commands

import java.util.*

/**
 * A input-port of CreateServerCommandUseCase.
 */
/**
 *
 */
interface CreateServerCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(ownerId: UUID, name: String, host: String?, port: UShort?, description: String)
}

/**
 *
 */
fun interface CreateServerCommandUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<UUID>)
}