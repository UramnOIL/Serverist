package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

fun interface FindUserByNameQuery {
    fun execute(dto: FindUserByNameDto)
}

data class FindUserByNameDto(val name: String)

fun interface FindUserByNameOutputPort {
    fun handle(dto: FindUserByNameOutputPortDto)
}

data class FindUserByNameOutputPortDto(val user: User?)
