package com.uramnoil.serverist.serverist.domain.models.server

import com.uramnoil.serverist.common.domain.models.kernel.UserId

class Server constructor(
    val userId: UserId,
    val createdAt: com.uramnoil.serverist.serverist.domain.models.server.CreatedAt,
    var name: Name,
    val ownerUserId: UserId,
    var host: com.uramnoil.serverist.serverist.domain.models.server.Host?,
    var port: Port?,
    var description: com.uramnoil.serverist.serverist.domain.models.server.Description
)
