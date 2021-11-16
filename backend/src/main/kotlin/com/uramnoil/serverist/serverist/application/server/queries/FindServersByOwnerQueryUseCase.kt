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
    fun execute(ownerId: UUID, limit: Int, offset: Long, sort: Sort, orderBy: OrderBy)
}

/**
 *
 */
fun interface FindServersByOwnerQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<List<Server>>)
}