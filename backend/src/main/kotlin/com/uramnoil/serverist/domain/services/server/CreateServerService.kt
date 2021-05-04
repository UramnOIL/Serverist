package com.uramnoil.serverist.domain.services.server

import com.uramnoil.serverist.domain.models.server.*
import com.uramnoil.serverist.domain.models.user.User

interface CreateServerService {
    suspend fun new(name: Name, owner: User, address: Address, port: Port, description: Description): Id
}