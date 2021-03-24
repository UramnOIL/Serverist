package com.uramnoil.serverist.application.usecases.user.queries

import java.util.*

fun interface FindServerByIdQuery {
    fun execute(dto: FindServerByIdQuery)
}

data class FindServerByIdDto(val id: UUID)

fun interface FindServerByIdOutputPort {
    fun handle(dto: FindUserByIdOutputPortDto?)
}

data class FindUserByIdOutputPortDto(val id: UUID, val name: String, val description: String)