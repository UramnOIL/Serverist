package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

data class FindUserByAccountIdQueryDto(val accountId: String)

fun interface FindUserByAccountIdQuery {
    fun execute(dto: FindUserByAccountIdQueryDto)
}

data class FindUserByAccountIdQueryOutputPortDto(val user: User?)

fun interface FindUserByAccountIdQueryOutputPort {
    fun handle(dto: FindUserByAccountIdQueryOutputPortDto)
}