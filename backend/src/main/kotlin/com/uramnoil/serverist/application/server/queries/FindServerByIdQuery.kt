package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.server.Server
import java.util.*

data class FindServerByIdDto(val id: UUID)

interface FindServerByIdQuery {
    suspend fun execute(dto: FindServerByIdDto): Server?
}