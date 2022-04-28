package com.uramnoil.serverist.serverist.domain.models.server

import com.uramnoil.serverist.kernel.domain.user.Id

class Server constructor(
    val id: Id,
    val createdAt: com.uramnoil.serverist.serverist.domain.models.server.CreatedAt,
    var name: Name,
    val ownerId: Id,
    var host: com.uramnoil.serverist.serverist.domain.models.server.Host?,
    var port: Port?,
    var description: com.uramnoil.serverist.serverist.domain.models.server.Description
)
