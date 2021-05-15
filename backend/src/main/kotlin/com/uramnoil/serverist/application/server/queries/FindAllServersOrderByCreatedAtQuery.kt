package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.server.Server

enum class Sort {
    Desc,
    Asc,
}

data class FindAllServersOrderByCreatedAtQueryDto(val sort: Sort)

interface FindAllServersOrderByCreatedAtQuery {
    fun execute(dto: FindAllServersOrderByCreatedAtQueryDto): List<Server>
}