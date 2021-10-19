package com.uramnoil.serverist.application.server.commands

import com.benasher44.uuid.Uuid


interface UpdateServerCommandInputPort {
    fun execute(
        id: Uuid,
        name: String,
        address: String?,
        port: Int?,
        description: String
    )
}

fun interface UpdateServerCommandOutputPort {
    fun handle(result: Result<Unit>)
}