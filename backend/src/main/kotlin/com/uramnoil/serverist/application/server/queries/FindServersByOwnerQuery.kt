package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.Server
import java.util.*

data class FindServersByOwnerQueryDto(
    val ownerId: UUID,
    val limit: Int,
    val offset: Long,
    val sort: Sort,
    val orderBy: OrderBy
)

interface FindServersByOwnerQuery {
    suspend fun execute(dto: FindServersByOwnerQueryDto): List<Server>
}