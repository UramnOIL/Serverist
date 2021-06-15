package com.uramnoil.serverist.application.server.queries

import java.util.*

data class CheckServerOwnerServiceDto(val serverId: UUID, val ownerId: UUID)

interface CheckServerOwnerService {
    suspend fun execute(dto: CheckServerOwnerServiceDto): Boolean
}