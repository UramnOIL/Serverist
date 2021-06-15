package com.uramnoil.serverist.domain.models.server.repositories

import com.uramnoil.serverist.domain.models.server.models.Id
import com.uramnoil.serverist.domain.models.server.models.Server

interface ServerRepository {
    suspend fun insert(server: Server)
    suspend fun update(server: Server)
    suspend fun delete(server: Server)
    suspend fun findById(id: Id): Server?
}