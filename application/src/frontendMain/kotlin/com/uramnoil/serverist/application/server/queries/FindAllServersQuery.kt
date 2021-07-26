package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.Server

actual interface FindAllServersQuery {
    fun execute(limit: Int, offset: Long, sort: Sort, orderBy: OrderBy): List<Server>
}

interface FindAllServersQueryOutputPort {
    fun handle(result: Result<List<Server>>)
}
