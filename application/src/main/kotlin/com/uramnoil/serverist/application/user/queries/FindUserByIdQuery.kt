package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User
import java.util.*

fun interface FindUserByIdQuery {
    fun execute(dto: FindUserByIdDto)
}

data class FindUserByIdDto(val id: UUID)

fun interface FindUserByIdOutputPort {
    fun handle(dto: FindUserByIdOutputPortDto?)
}

data class FindUserByIdOutputPortDto(val user: User?)