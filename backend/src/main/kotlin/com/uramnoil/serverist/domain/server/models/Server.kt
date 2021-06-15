package com.uramnoil.serverist.domain.server.models

class Server internal constructor(
    val id: com.uramnoil.serverist.domain.server.models.Id,
    val createdAt: com.uramnoil.serverist.domain.server.models.CreatedAt,
    var name: com.uramnoil.serverist.domain.server.models.Name,
    val ownerId: com.uramnoil.serverist.domain.kernel.models.UserId,
    var address: com.uramnoil.serverist.domain.server.models.Address,
    var port: com.uramnoil.serverist.domain.server.models.Port,
    var description: com.uramnoil.serverist.domain.server.models.Description
)