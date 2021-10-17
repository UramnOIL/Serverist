package com.uramnoil.serverist.application.serverist.queries

import com.benasher44.uuid.Uuid

interface IsUserOwnerOfServer {
    fun execute(ownerId: Uuid, serverId: Uuid)
}

interface IsUserOwnerOfServerOutputPort {
    fun handle(result: Result<Boolean>)
}