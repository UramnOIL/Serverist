package com.uramnoil.serverist.application.user.commands

import com.benasher44.uuid.Uuid

/**
 *
 */
interface UpdateUserCommandUseCaseInputPortForServer {
    /**
     *
     */
    fun execute(id: Uuid, accountId: String, name: String, description: String)
}

/**
 *
 */
interface UpdateUserCommandUseCaseInputPortForClient {
    /**
     *
     */
    fun execute(accountId: String, name: String, description: String)
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