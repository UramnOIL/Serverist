package com.uramnoil.serverist.application.service.usecases.user.queries

import java.util.*

fun interface FindUserByIdQuery {
    fun execute(dto: FindUserByIdDto)
}

data class FindUserByIdDto(val id: UUID)

fun interface FindUserByIdOutputPort {
    fun handle(dto: FindUserByIdOutputPortDto?)
}

data class FindUserByIdOutputPortDto(val id: UUID, val name: String, val description: String)