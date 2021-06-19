package com.uramnoil.serverist.domain.server.services

import com.uramnoil.serverist.domain.server.models.*
import com.uramnoil.serverist.domain.user.models.User

data class CreateServerServiceDto(
    val name: Name,
    val owner: User,
    val address: Address,
    val port: Port,
    val description: Description
)

interface CreateServerService {
    suspend fun new(dto: CreateServerServiceDto): Server
}