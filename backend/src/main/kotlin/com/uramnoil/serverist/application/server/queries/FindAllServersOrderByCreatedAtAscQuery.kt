package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.server.Server

interface FindAllServersOrderByCreatedAtAscQuery {
    fun execute(): List<Server>
}