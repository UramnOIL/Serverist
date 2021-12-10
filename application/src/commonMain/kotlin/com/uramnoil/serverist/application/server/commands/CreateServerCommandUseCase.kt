package com.uramnoil.serverist.application.server.commands

import com.benasher44.uuid.Uuid


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
    fun execute(ownerId: Uuid, name: String, host: String?, port: UShort?, description: String)
}

/**
 *
 */
fun interface CreateServerCommandUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Uuid>)
}