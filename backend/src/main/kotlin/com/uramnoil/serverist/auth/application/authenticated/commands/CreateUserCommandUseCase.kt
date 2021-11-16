package com.uramnoil.serverist.auth.application.authenticated.commands

import java.util.*


/**
 *
 */
interface CreateUserCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(email: String, password: String)
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