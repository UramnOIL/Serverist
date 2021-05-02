package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.server.Server

data class FindServerByIdDto(val id: String)

interface FindServerByIdQuery {
    suspend fun execute(dto: FindServerByIdDto): Server?
}