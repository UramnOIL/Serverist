package com.uramnoil.serverist.application.server.commands

import java.util.*

interface DeleteServerCommand {
    suspend fun execute(id: UUID)
}
