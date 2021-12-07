package com.uramnoil.serverist.serverist.application.server.services

import com.benasher44.uuid.Uuid


interface ServerService {
    suspend fun checkUserIsOwnerOfServer(ownerId: Uuid, serverId: Uuid): Result<Boolean>
}