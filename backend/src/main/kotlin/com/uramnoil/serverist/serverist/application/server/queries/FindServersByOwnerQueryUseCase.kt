package com.uramnoil.serverist.serverist.application.server.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.Sort
import com.uramnoil.serverist.serverist.application.server.Server

interface FindServersByOwnerQueryUseCaseInputPort {
    suspend fun execute(
        ownerId: Uuid,
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    ): Result<List<Server>>
}