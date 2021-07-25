package com.uramnoil.serverist.application.server.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.Server

actual interface FindServerByIdQuery {
    suspend fun execute(id: Uuid): Server?
}