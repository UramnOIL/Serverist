package com.uramnoil.serverist.domain.models.server.services

import com.uramnoil.serverist.domain.models.server.models.*
import com.uramnoil.serverist.domain.models.user.models.User

interface CreateServerService {
    suspend fun new(name: Name, owner: User, address: Address, port: Port, description: Description): Server
}