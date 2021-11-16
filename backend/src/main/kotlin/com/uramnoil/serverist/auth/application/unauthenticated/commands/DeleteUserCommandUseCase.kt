package com.uramnoil.serverist.auth.application.unauthenticated.commands

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
