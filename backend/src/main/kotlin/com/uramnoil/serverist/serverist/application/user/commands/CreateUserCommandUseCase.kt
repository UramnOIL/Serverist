package com.uramnoil.serverist.serverist.application.user.commands

import java.util.*

/**
 *
 */
interface CreateUserCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(id: UUID, accountId: String, name: String, description: String)
}

/**
 *
 */
fun interface CreateUserCommandUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<UUID>)
}