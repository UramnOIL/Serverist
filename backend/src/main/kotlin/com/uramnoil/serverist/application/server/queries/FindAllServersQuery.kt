package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.server.Server

enum class Sort {
    Desc,
    Asc,
}

enum class OrderBy {
    CreatedAt,
}

data class FindAllServerQueryDto(val limit: Int, val offset: Long, val sort: Sort, val orderBy: OrderBy)

interface FindAllServersQuery {
    suspend fun execute(dto: FindAllServerQueryDto): List<Server>
}