package com.uramnoil.serverist.domain.serverist.models.server

import com.uramnoil.serverist.kernel.domain.user.Id

class Server constructor(
    val id: Id,
    val createdAt: CreatedAt,
    var name: Name,
    val ownerId: Id,
    var host: Host?,
    var port: Port?,
    var description: Description
)
