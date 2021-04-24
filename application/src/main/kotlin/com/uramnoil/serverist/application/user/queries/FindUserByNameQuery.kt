package com.uramnoil.serverist.application.user.queries

import java.util.*

fun interface FindUserByNameQuery {
    fun excecute(dto: FindUserByNameDto)
}

data class FindUserByNameDto(val name: String)

fun interface FindUserByNameOutputPort {
    fun handle(dto: FindUserByNameOutputPortDto?)
}

data class FindUserByNameOutputPortDto(val id: UUID, val name: String, val description: String)
