package com.uramnoil.serverist.domain.serverist.repositories

import com.uramnoil.serverist.domain.serverist.models.server.Id
import com.uramnoil.serverist.domain.serverist.models.server.Server

interface ServerRepository {
    suspend fun insert(server: Server): Result<Unit>
    suspend fun update(server: Server): Result<Unit>
    suspend fun delete(server: Server): Result<Unit>
    suspend fun findById(id: Id): Result<Server?>
}
