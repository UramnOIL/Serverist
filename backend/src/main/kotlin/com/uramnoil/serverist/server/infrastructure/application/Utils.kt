package com.uramnoil.serverist.server.infrastructure.application

import com.uramnoil.serverist.domain.server.models.Server
import com.uramnoil.serverist.server.application.Server as ApplicationServer

fun Server.toApplication() = ApplicationServer(
    id = id.value,
    createdAt = createdAt.value,
    ownerId = ownerId.value,
    name = name.value,
    address = address.value,
    port = port.value,
    description = description.value
)