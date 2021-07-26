package com.uramnoil.serverist.application.server.queries

import com.benasher44.uuid.Uuid

actual interface IsUserOwnerOfServer {
    fun execute(ownerId: Uuid, serverId: Uuid): Boolean
}

interface IsUserOwnerOfServerOutputPort {
    fun handle(result: Result<Boolean>)
}