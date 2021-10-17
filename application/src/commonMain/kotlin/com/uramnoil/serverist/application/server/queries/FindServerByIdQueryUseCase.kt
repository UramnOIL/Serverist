package com.uramnoil.serverist.application.server.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.Server

interface FindServerByIdQueryUseCaseInputPort {
    fun execute(id: Uuid)
}

interface FindServerByIdQueryUseCaseOutputPort {
    fun handle(result: Result<Server?>)
}
