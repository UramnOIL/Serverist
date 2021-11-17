package com.uramnoil.serverist.serverist.infrastructure.application.server

import com.uramnoil.serverist.domain.serverist.models.server.Server
import com.uramnoil.serverist.application.server.Server as ApplicationServer

fun Server.toApplication() = ApplicationServer(
    id = id.value,
    createdAt = createdAt.value,
    ownerId = ownerId.value,
    name = name.value,
    host = host?.value,
    port = port?.value?.toInt(),
    description = description.value
)