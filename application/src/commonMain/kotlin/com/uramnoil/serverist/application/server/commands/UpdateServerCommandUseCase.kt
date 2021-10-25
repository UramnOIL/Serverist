package com.uramnoil.serverist.application.server.commands

import com.benasher44.uuid.Uuid


interface UpdateServerCommandUseCaseInputPort {
    fun execute(
        id: Uuid,
        name: String,
        address: String?,
        port: Int?,
        description: String
    )
}

fun interface UpdateServerCommandUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}