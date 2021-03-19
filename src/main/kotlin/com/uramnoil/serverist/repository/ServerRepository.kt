package com.uramnoil.serverist.repository

import com.uramnoil.serverist.model.server.Id
import com.uramnoil.serverist.model.server.Server

interface ServerRepository {
    suspend fun findById(id: Id): Server
    suspend fun getNextId(): Id
    suspend fun store(server: Server)
    suspend fun remove(server: Server)
}