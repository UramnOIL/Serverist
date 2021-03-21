package com.uramnoil.serverist.service.models.server

import com.uramnoil.serverist.service.models.user.Id as UserId

class Server(val id: Id, var name: Name, val owner: UserId, var address: Address, var port: Port, var description: Description) {
}