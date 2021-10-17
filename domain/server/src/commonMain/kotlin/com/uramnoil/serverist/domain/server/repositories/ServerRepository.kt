package com.uramnoil.serverist.domain.server.repositories

import com.uramnoil.serverist.domain.server.models.Id
import com.uramnoil.serverist.domain.server.models.Server


interface ServerRepository {
    suspend fun insert(server: Server): Result<Unit>
    suspend fun update(server: Server): Result<Unit>
    suspend fun delete(server: Server): Result<Unit>
    suspend fun findById(id: Id): Result<Server?>
}