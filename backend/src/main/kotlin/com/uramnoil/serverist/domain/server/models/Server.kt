package com.uramnoil.serverist.domain.server.models

import com.uramnoil.serverist.domain.kernel.models.user.UserId

class Server internal constructor(
    val id: Id,
    val createdAt: CreatedAt,
    var name: Name,
    val ownerId: UserId,
    var address: Address,
    var port: Port,
    var description: Description
)