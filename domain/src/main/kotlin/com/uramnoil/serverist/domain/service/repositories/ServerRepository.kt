package com.uramnoil.serverist.domain.service.repositories

import com.uramnoil.serverist.domain.service.models.server.*
import kotlinx.coroutines.Deferred

interface ServerRepository {
    fun findByIdAsync(id: Id): Deferred<Server>
    fun storeAsync(server: Server): Deferred<Unit>
    fun removeAsync(server: Server): Deferred<Unit>
}

object ServerFactory {
    internal fun create(id: String, name: String, ownerId: String, address: String?, port: Int?, description: String) =
        Server(
            Id(id),
            Name(name),
            com.uramnoil.serverist.domain.service.models.user.Id(ownerId),
            Address(address),
            Port(port),
            Description(description)
        )
}