package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.server.Server

enum class Sort {
    Desc,
    Asc,
}

enum class OrderBy {
    CreatedAt,
}

data class FindAllServerDto(val limit: Int, val offset: Int, val sort: Sort, val orderBy: OrderBy)

interface FindAllServersOrderByCreatedAtQuery {
    fun execute(dto: FindAllServerDto): List<Server>
}