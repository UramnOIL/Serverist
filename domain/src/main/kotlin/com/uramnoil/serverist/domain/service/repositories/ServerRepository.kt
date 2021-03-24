package com.uramnoil.serverist.domain.service.repositories

import com.uramnoil.serverist.domain.service.models.server.Id
import com.uramnoil.serverist.domain.service.models.server.Server
import kotlinx.coroutines.Deferred

interface ServerRepository {
    fun findByIdAsync(id: Id): Deferred<Server>
    fun storeAsync(server: Server): Deferred<Unit>
    fun removeAsync(server: Server): Deferred<Unit>
}