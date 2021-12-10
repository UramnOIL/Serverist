package com.uramnoil.serverist.application.server

import com.benasher44.uuid.Uuid


/**
 *
 */
data class DeleteServerCommandUseCaseInput(val id: Uuid)

/**
 *
 */
interface DeleteServerCommandUseCaseInputPort {
    fun execute(input: DeleteServerCommandUseCaseInput)
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