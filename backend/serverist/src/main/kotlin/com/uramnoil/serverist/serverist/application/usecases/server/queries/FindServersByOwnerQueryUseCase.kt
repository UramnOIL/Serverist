package com.uramnoil.serverist.serverist.application.usecases.server.queries

import com.uramnoil.serverist.serverist.application.usecases.OrderBy
import com.uramnoil.serverist.serverist.application.usecases.Sort
import java.util.UUID

/**
 *
 */
interface FindServersByOwnerQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(ownerId: UUID, limit: Int, offset: Long, sort: Sort, orderBy: OrderBy): Result<List<ServerDto>>
}

