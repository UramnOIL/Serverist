package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.Server
import java.util.*

interface FindServersByOwnerQuery {
    suspend fun execute(
        ownerId: UUID,
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ): List<Server>
}