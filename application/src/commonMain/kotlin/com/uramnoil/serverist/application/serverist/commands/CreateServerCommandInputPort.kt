package com.uramnoil.serverist.application.serverist.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.serverist.Server

interface CreateServerCommandInputPort {
    fun execute(
        ownerId: Uuid,
        name: String,
        address: String?,
        port: Int?,
        description: String
    )
}

interface CreateServerCommandOutputPort {
    fun handle(result: Result<Server>)
}