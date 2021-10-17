package com.uramnoil.serverist.application.serverist.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.serverist.Server

interface FindServersByOwnerQueryInputPort {
    fun execute(
        ownerId: Uuid,
        limit: Int,
        offset: Long,
        sort: Sort,
        orderBy: OrderBy
    )
}

interface FindServersByOwnerQueryOutputPort {
    fun handle(result: Result<List<Server>>)
}