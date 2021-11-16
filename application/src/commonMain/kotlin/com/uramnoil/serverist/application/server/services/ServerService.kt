package com.uramnoil.serverist.application.server.services

import java.util.*

interface ServerService {
    suspend fun checkUserIsOwnerOfServer(ownerId: UUID, serverId: UUID): Result<Boolean>
}