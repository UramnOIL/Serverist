package com.uramnoil.serverist.application.authenticated.commands

import com.benasher44.uuid.Uuid


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
    fun handle(result: Result<Uuid>)
}