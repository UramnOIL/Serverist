package com.uramnoil.serverist.serverist.domain.repositories

import com.uramnoil.serverist.serverist.domain.models.server.Id
import com.uramnoil.serverist.serverist.domain.models.server.Server

interface ServerRepository {
    suspend fun insert(server: Server): Result<Unit>
    suspend fun update(server: Server): Result<Unit>
    suspend fun delete(server: Server): Result<Unit>
    suspend fun findById(id: Id): Result<Server?>
}
