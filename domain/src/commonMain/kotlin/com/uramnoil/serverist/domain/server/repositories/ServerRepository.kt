package com.uramnoil.serverist.domain.server.repositories

import com.uramnoil.serverist.domain.server.models.Server

interface ServerRepository {
    suspend fun insert(server: Server): Result<Unit>
    suspend fun update(server: Server): Result<Unit>
    suspend fun delete(server: Server): Result<Unit>
    suspend fun findById(id: com.uramnoil.serverist.domain.server.models.Id): Result<Server?>
}