package com.uramnoil.serverist.application.server.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.Server

actual interface CreateServerCommandInputPort {
    fun execute(
        ownerId: Uuid,
        name: String,
        address: String?,
        port: Int?,
        description: String
    ): Server
}

interface CreateServerCommandOutputPort {
    fun handle(result: Result<Server>)
}