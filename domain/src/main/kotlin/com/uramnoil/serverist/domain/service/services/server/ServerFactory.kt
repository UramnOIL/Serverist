package com.uramnoil.serverist.domain.service.services.server

import com.uramnoil.serverist.domain.service.models.server.*
import java.util.*

interface ServerFactory {
    fun create(id: UUID, name: String, ownerId: UUID, address: String?, port: Int?, description: String) =
        Server(
            Id(id),
            Name(name),
            com.uramnoil.serverist.domain.service.models.user.Id(ownerId),
            Address(address),
            Port(port),
            Description(description)
        )
}