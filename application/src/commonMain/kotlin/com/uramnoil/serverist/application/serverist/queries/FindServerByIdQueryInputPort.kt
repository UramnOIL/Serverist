package com.uramnoil.serverist.application.serverist.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.serverist.Server

interface FindServerByIdQueryInputPort {
    fun execute(id: Uuid)
}

interface FindServerByIdQueryOutputPort {
    fun handle(result: Result<Server?>)
}
