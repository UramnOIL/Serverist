package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

data class FindUserByAccountIdDto(val accountId: String)

fun interface FindUserByAccountIdQuery {
    fun execute(dto: FindUserByAccountIdDto)
}

data class FindUserByAccountIdOutputPortDto(val user: User?)

fun interface FindUserByAccountIdOutputPort {
    fun handle(dto: FindUserByAccountIdOutputPortDto)
}