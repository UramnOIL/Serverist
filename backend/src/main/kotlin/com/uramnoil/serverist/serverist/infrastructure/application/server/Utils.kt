package com.uramnoil.serverist.serverist.infrastructure.application

import com.uramnoil.serverist.domain.serverist.models.server.Server
import com.uramnoil.serverist.serverist.application.server.Server as ApplicationServer

fun Server.toApplication() = ApplicationServer(
    id = id.value,
    createdAt = createdAt.value,
    ownerId = ownerId.value,
    name = name.value,
    address = address.value,
    port = port.value,
    description = description.value
)