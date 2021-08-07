package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.Server

actual interface FindAllServersQueryInputPort {
    suspend fun execute(limit: Int, offset: Long, sort: Sort, orderBy: OrderBy): Result<List<Server>>
}