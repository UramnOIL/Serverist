package com.uramnoil.serverist.application.server.queries

import com.benasher44.uuid.Uuid

interface IsUserOwnerOfServerQueryUseCaseInputPort {
    fun execute(ownerId: Uuid, serverId: Uuid)
}

interface IsUserOwnerOfServerQueryUseCaseOutputPort {
    fun handle(result: Result<Boolean>)
}