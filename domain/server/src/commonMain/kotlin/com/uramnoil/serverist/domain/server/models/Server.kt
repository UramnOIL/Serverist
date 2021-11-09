package com.uramnoil.serverist.domain.server.models

import com.uramnoil.serverist.domain.common.user.Id


class Server constructor(
    val id: Id,
    val createdAt: CreatedAt,
    var name: Name,
    val ownerId: Id,
    var address: Address,
    var port: Port,
    var description: Description
)