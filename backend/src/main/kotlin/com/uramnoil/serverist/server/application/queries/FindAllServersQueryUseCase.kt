package com.uramnoil.serverist.server.application.queries

import com.uramnoil.serverist.Sort
import com.uramnoil.serverist.server.application.Server

interface FindAllServersQueryUseCaseInputPort {
    suspend fun execute(limit: Int, offset: Long, sort: Sort, orderBy: OrderBy): Result<List<Server>>
}