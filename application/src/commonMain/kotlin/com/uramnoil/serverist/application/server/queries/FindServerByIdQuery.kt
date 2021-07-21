package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.server.Server
import java.util.*

interface FindServerByIdQuery {
    suspend fun execute(id: UUID): Server?
}