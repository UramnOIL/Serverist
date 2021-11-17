package com.uramnoil.serverist.application.unauthenticated.commands

import java.util.*


/**
 *
 */
interface DeleteUserCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(id: UUID)
}

/**
 *
 */
fun interface DeleteUserCommandUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Unit>)
}