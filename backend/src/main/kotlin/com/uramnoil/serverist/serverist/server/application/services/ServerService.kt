package com.uramnoil.serverist.serverist.server.application.services

import com.benasher44.uuid.Uuid

interface ServerService {
    suspend fun checkUserIsOwnerOfServer(ownerId: Uuid, serverId: Uuid): Result<Boolean>
}