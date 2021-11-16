package com.uramnoil.serverist.serverist.application.server.queries

import com.uramnoil.serverist.Sort
import com.uramnoil.serverist.serverist.application.server.Server

/**
 *
 */
interface FindAllServersQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(limit: Int, offset: Long, sort: Sort, orderBy: OrderBy)
}

/**
 *
 */
fun interface FindAllServersQueryUseCaseOutputPort {
    /**
     *
     */
    suspend fun handle(result: Result<List<Server>>)
}