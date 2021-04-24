package com.uramnoil.serverist.domain.repositories

import com.uramnoil.serverist.domain.models.server.Server
import kotlinx.coroutines.Deferred

interface ServerRepository {
    fun findByIdAsync(id: com.uramnoil.serverist.domain.models.server.Id): Deferred<Server?>
    fun storeAsync(server: Server): Deferred<Unit>
    fun deleteAsync(server: Server): Deferred<Unit>
}