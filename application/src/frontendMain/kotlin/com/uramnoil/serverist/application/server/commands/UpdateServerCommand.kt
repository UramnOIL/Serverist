package com.uramnoil.serverist.application.server.commands

import com.benasher44.uuid.Uuid


actual interface UpdateServerCommand {
    fun execute(
        id: Uuid,
        name: String,
        address: String?,
        port: Int?,
        description: String
    )
}

interface UpdateServerCommandOutputPort {
    fun handle(result: Result<Unit>)
}