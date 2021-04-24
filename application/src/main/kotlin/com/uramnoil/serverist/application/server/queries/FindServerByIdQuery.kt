package com.uramnoil.serverist.application.server.queries

import java.util.*

fun interface FindServerByIdQuery {
    fun execute(dto: FindServerByIdDto)
}

class FindServerByIdDto(val id: UUID)


fun interface FindServerByIdOutputPort {
    fun handle(dto: FindServerByIdOutputPortDto?)
}

data class FindServerByIdOutputPortDto(
    val id: UUID,
    val ownerId: UUID,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String
)