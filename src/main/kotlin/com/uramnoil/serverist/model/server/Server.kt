package com.uramnoil.serverist.model.server

import com.uramnoil.serverist.model.user.Id as UserId

class Server(val id: Id, var name: Name, val owner: UserId, var address: Address, var port: Port, var description: Description) {
}