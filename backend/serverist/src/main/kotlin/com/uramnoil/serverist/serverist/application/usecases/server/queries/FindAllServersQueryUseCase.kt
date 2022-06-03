package com.uramnoil.serverist.serverist.application.usecases.server.queries

import com.uramnoil.serverist.serverist.application.usecases.OrderBy
import com.uramnoil.serverist.serverist.application.usecases.Sort


/**
 *
 */
interface FindAllServersQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(limit: Int, offset: Long, sort: Sort, orderBy: OrderBy): Result<List<ServerWithOwnerDto>>
}

