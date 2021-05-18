package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.Server

data class FindServersByOwnerDto(val limit: Int, val offset: Long, val sort: Sort, val orderBy: OrderBy)

interface FindServersByOwnerQuery {
    suspend fun execute(dto: FindAllServerQueryDto): List<Server>
}