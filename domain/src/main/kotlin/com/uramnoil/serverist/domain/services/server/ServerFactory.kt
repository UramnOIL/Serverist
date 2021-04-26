package com.uramnoil.serverist.domain.services.server

import com.uramnoil.serverist.domain.models.server.*
import java.util.*
import com.uramnoil.serverist.domain.models.user.Id as UserId

object ServerFactory {
    fun create(id: String, name: String, ownerId: String, address: String?, port: Int?, description: String) =
        Server(
            Id(UUID.fromString(id)),
            Name(name),
            UserId(UUID.fromString(ownerId)),
            Address(address),
            Port(port),
            Description(description)
        )
}