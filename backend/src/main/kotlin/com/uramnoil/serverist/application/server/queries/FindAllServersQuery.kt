package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.Server

data class FindAllServerQueryDto(val limit: Int, val offset: Long, val sort: Sort, val orderBy: OrderBy)

interface FindAllServersQuery {
    suspend fun execute(dto: FindAllServerQueryDto): List<Server>
}