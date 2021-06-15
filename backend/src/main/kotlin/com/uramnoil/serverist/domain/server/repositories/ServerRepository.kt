package com.uramnoil.serverist.domain.server.repositories

import com.uramnoil.serverist.domain.server.models.Server

interface ServerRepository {
    suspend fun insert(server: Server)
    suspend fun update(server: Server)
    suspend fun delete(server: Server)
    suspend fun findById(id: com.uramnoil.serverist.domain.server.models.Id): Server?
}