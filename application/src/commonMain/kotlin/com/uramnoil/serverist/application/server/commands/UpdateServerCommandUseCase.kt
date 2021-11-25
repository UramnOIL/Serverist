package com.uramnoil.serverist.application.server.commands

import com.benasher44.uuid.Uuid


/**
 *
 */
interface UpdateServerCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(id: Uuid, name: String, host: String?, port: UShort?, description: String)
}

/**
 *
 */
fun interface UpdateServerCommandUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Unit>)
}