package com.uramnoil.serverist.serverist.application.server.commands

import com.benasher44.uuid.Uuid

/**
 *
 */
interface DeleteServerCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(id: Uuid)
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
