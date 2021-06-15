package com.uramnoil.serverist.application.server.queries

import java.util.*

data class IsUserOwnerOfServerDto(val ownerId: UUID, val serverId: UUID)

interface IsUserOwnerOfServer {
    suspend fun execute(dto: IsUserOwnerOfServerDto): Boolean
}