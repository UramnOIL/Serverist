package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

fun interface FindUserByIdQuery {
    fun execute(dto: FindUserByIdDto)
}

data class FindUserByIdDto(val id: String)

fun interface FindUserByIdOutputPort {
    fun handle(dto: FindUserByIdOutputPortDto?)
}

data class FindUserByIdOutputPortDto(val user: User?)