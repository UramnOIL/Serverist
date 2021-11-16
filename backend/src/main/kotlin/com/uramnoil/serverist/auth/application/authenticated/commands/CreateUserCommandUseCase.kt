package com.uramnoil.serverist.auth.application.authenticated.commands

import java.util.*


/**
 *
 */
interface CreateUserCommandUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(email: String, password: String)
}

interface CreateUserCommandUseCaseOutputPort {
    /**
     *
     */
    suspend fun handle(result: Result<UUID>)
}