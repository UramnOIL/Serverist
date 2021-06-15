package com.uramnoil.serverist.domain.services.server

import com.uramnoil.serverist.domain.models.server.*
import kotlinx.datetime.Instant
import kotlinx.datetime.toKotlinInstant
import java.time.LocalDateTime
import java.util.*
import com.uramnoil.serverist.domain.models.user.Id as UserId
import java.time.Instant as JavaInstant

object ServerFactory {
    fun create(
        id: UUID,
        createdAt: Instant,
        name: String,
        ownerId: UUID,
        address: String?,
        port: Int?,
        description: String
    ) =
        Server(
            Id(id),
            CreatedAt(createdAt),
            Name(name),
            UserId(ownerId),
            Address(address),
            Port(port),
            Description(description)
        )

    fun create(
        id: UUID,
        createdAt: LocalDateTime,
        name: String,
        ownerId: UUID,
        address: String?,
        port: Int?,
        description: String
    ) = create(
        id = id,
        createdAt = JavaInstant.from(createdAt).toKotlinInstant(),
        name = name,
        ownerId = ownerId,
        address = address,
        port = port,
        description = description
    )
}