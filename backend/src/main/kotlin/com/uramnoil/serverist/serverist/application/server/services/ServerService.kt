package com.uramnoil.serverist.serverist.application.server.services

import java.util.*

interface ServerService {
    suspend fun checkUserIsOwnerOfServer(ownerId: UUID, serverId: UUID): Result<Boolean>
}