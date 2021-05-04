package com.uramnoil.serverist.application.server.queries

import com.uramnoil.serverist.application.server.Server

data class FindServerByIdDto(val id: String)

fun interface FindServerByIdQuery {
    fun execute(dto: FindServerByIdDto)
}

data class FindServerByIdOutputPortDto(val server: Server)

fun interface FindServerByIdOutputPort {
    fun handle(dto: FindServerByIdOutputPortDto?)
}