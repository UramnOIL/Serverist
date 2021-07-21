package com.uramnoil.serverist.application.server.queries

import java.util.*

interface IsUserOwnerOfServer {
    suspend fun execute(ownerId: UUID, serverId: UUID): Boolean
}