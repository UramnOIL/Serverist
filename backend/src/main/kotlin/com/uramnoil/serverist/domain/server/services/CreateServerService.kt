package com.uramnoil.serverist.domain.server.services

import com.uramnoil.serverist.domain.server.models.*
import com.uramnoil.serverist.domain.user.models.User

interface CreateServerService {
    suspend fun new(
        name: Name,
        owner: User,
        address: Address,
        port: Port,
        description: Description
    ): Server
}