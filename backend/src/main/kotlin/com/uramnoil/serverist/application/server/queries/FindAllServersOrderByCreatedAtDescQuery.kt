package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.server.Server

interface FindAllServersOrderByCreatedAtDescQuery {
    fun execute(): List<Server>
}