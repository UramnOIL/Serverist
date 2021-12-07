package com.uramnoil.serverist.serverist.infrastructure.application.user.commands

import com.benasher44.uuid.Uuid

/**
 *
 */
interface DeleteUserCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(id: Uuid)
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