package com.uramnoil.serverist.application.server.commands

import java.util.*

/**
 *
 */
interface DeleteServerCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(id: UUID)
}

/**
 *
 */
fun interface DeleteServerCommandUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Unit>)
}