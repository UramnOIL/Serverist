package com.uramnoil.serverist.domain.service.repositories

import com.uramnoil.serverist.domain.service.models.server.Id
import com.uramnoil.serverist.service.models.server.Server

interface ServerRepository {
    suspend fun findById(id: Id): Server
    suspend fun getNextId(): Id
    suspend fun store(server: Server)
    suspend fun remove(server: Server)
}