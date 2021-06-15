package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.domain.models.server.Server
import com.uramnoil.serverist.domain.services.server.ServerFactory
import org.jetbrains.exposed.sql.ResultRow

object ExposedServerFactory {
    fun create(result: ResultRow): Server {
        return ServerFactory.create(
            result[Servers.id].value,
            result[Servers.createdAt],
            result[Servers.name],
            result[Servers.owner],
            result[Servers.address],
            result[Servers.port],
            result[Servers.description]
        )
    }
}