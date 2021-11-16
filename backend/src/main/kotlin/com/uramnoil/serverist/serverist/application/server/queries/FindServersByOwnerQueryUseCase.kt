package com.uramnoil.serverist.serverist.application.server.queries

import com.uramnoil.serverist.Sort
import com.uramnoil.serverist.serverist.application.server.Server
import java.util.*

/**
 *
 */
interface FindServersByOwnerQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(ownerId: UUID, limit: Int, offset: Long, sort: Sort, orderBy: OrderBy)
}

interface FindServersByOwnerQueryUseCaseOutputPort {
    /**
     *
     */
    suspend fun handle(result: Result<List<Server>>)
}