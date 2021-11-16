package com.uramnoil.serverist.application.user.commands

import java.util.*

/**
 *
 */
interface UpdateUserCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(id: UUID, accountId: String, name: String, description: String)
}

/**
 *
 */
fun interface UpdateUserCommandUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Unit>)
}