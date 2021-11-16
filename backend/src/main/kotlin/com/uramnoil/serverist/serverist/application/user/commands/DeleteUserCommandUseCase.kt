package com.uramnoil.serverist.serverist.application.user.commands

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