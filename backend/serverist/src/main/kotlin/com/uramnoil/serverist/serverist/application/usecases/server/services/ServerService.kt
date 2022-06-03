package com.uramnoil.serverist.serverist.application.usecases.server.services

import java.util.UUID

interface ServerService {
    suspend fun checkUserIsOwnerOfServer(ownerId: UUID, serverId: UUID): Result<Boolean>
}
