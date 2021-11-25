package com.uramnoil.serverist.application.user.commands

import com.benasher44.uuid.Uuid

/**
 *
 */
interface UpdateUserCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(id: Uuid, accountId: String, name: String, description: String)
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