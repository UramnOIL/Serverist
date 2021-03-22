package com.uramnoil.serverist.domain.service.repositories

import com.uramnoil.serverist.domain.service.models.server.*
import com.uramnoil.serverist.domain.service.models.user.User
import kotlinx.coroutines.Deferred

interface ServerRepository {
    fun findById(id: Id): Deferred<Server>
    fun getNextId(): Id
    fun store(server: Server): Deferred<Unit>
    fun remove(server: Server): Deferred<Unit>
}

class ServerFactory(val repository: ServerRepository) {
    fun new(name: String, owner: User, address: String?, port: Int?, description: String) =
        Server(repository.getNextId(), Name(name), owner.id, Address(address), Port(port), Description(description))

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