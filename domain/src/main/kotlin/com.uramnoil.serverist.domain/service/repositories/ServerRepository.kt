package com.uramnoil.serverist.domain.service.repositories

import com.uramnoil.serverist.domain.service.models.server.Id
import com.uramnoil.serverist.domain.service.models.server.Server
import kotlinx.coroutines.Deferred

interface ServerRepository {
    fun findById(id: Id): Deferred<Server>
    fun getNextId(): Deferred<Id>
    fun store(server: Server): Deferred<Unit>
    fun remove(server: Server): Deferred<Unit>
}