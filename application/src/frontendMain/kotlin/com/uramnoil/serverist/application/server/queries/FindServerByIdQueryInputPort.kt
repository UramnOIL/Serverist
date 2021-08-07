package com.uramnoil.serverist.application.server.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.Server

actual interface FindServerByIdQueryInputPort {
    fun execute(id: Uuid): Server?
}

interface FindServerByIdQueryOutputPort {
    fun handle(result: Result<Server?>)
}
