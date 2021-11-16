package com.uramnoil.serverist.serverist.application.server.commands

import java.util.*


/**
 *
 */
interface UpdateServerCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(id: UUID, name: String, host: String?, port: UShort?, description: String)
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