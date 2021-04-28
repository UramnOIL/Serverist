package com.uramnoil.serverist.domain.repositories

import com.uramnoil.serverist.domain.models.server.Server

interface ServerRepository {
    suspend fun findById(id: com.uramnoil.serverist.domain.models.server.Id): Server?
    suspend fun store(server: Server)
    suspend fun delete(server: Server)
}