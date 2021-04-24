package com.uramnoil.serverist.domain.models.server

import com.uramnoil.serverist.domain.models.user.Id as UserId

class Server internal constructor(
    val id: Id,
    var name: Name,
    val owner: UserId,
    var address: Address,
    var port: Port,
    var description: Description
)