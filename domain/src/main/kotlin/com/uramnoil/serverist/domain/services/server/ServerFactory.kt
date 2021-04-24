package com.uramnoil.serverist.domain.services.server

import com.uramnoil.serverist.domain.models.server.*
import java.util.*
import com.uramnoil.serverist.domain.models.user.Id as UserId

object ServerFactory {
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