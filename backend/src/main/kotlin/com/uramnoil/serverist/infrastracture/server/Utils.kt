package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.domain.models.server.Server
import com.uramnoil.serverist.application.server.Server as ApplicationServer

fun Server.toApplication() = ApplicationServer(
    id = id.value,
    createdAt = createdAt.value,
    ownerId = ownerId.value,
    name = name.value,
    address = address.value,
    port = port.value,
    description = description.value
)