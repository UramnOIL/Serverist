package com.uramnoil.serverist.application.unauthenticateduser.queries

import com.uramnoil.serverist.application.unauthenticateduser.User

data class FindUnauthenticatedUserByAccountIdQueryDto(val id: String)

fun interface FindUnauthenticatedUserByAccountIdQuery {
    fun execute(dto: FindUnauthenticatedUserByAccountIdQueryDto)
}

data class FindUnauthenticatedUserByAccountIdQueryOutputPortDto(val user: User?)

fun interface FindUnauthenticatedUserByAccountIdQueryOutputPort {
    fun handle(dto: FindUnauthenticatedUserByAccountIdQueryOutputPortDto)
}
