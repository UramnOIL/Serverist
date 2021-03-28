package com.uramnoil.serverist.application.usecases.user.queries

import java.util.*

fun interface FindUserByNameQuery {
    fun excecute(dto: FindUserByNameDto)
}

data class FindUserByNameDto(val name: String)

fun interface FindUserByNameOutputPort {
    fun handle(dto: FindUserByNameDto?)
}

data class FinUserByNameOutputPortDto(val id: UUID, val name: String, val description: String)
