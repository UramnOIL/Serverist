package com.uramnoil.serverist.application.serverist.queries

import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.serverist.Server

interface FindAllServersQueryInputPort {
    fun execute(limit: Int, offset: Long, sort: Sort, orderBy: OrderBy)
}

interface FindAllServersQueryOutputPort {
    fun handle(result: Result<List<Server>>)
}
