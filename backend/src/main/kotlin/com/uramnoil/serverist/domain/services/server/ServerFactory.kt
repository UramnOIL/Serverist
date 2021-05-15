package com.uramnoil.serverist.domain.services.server

import com.uramnoil.serverist.domain.models.server.*
import java.util.*
import com.uramnoil.serverist.domain.models.user.Id as UserId

object ServerFactory {
    fun create(id: String, name: String, ownerId: String, address: String?, port: Int?, description: String) =
        create(
            UUID.fromString(id),
            name,
            UUID.fromString(ownerId),
            address,
            port,
            description
        )

    fun create(id: UUID, name: String, ownerId: UUID, address: String?, port: Int?, description: String) =
        Server(
            Id(id),
            Name(name),
            UserId(ownerId),
            Address(address),
            Port(port),
            Description(description)
        )
}