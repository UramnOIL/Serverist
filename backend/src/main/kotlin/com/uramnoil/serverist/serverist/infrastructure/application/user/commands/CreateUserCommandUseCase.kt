package com.uramnoil.serverist.serverist.infrastructure.application.user.commands

import com.benasher44.uuid.Uuid

/**
 *
 */
interface CreateUserCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(id: Uuid, accountId: String, name: String, description: String)
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