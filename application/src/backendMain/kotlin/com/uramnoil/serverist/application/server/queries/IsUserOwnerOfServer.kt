package com.uramnoil.serverist.application.server.queries

import com.benasher44.uuid.Uuid

actual interface IsUserOwnerOfServer {
    suspend fun execute(ownerId: Uuid, serverId: Uuid): Boolean
}