package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.Server

/**
 *
 */
interface FindAllServersQueryUseCaseInputPort {
    /**
     *
     */
    fun execute(limit: Int, offset: Long, sort: Sort, orderBy: OrderBy)
}

/**
 *
 */
fun interface FindAllServersQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<List<Server>>)
}