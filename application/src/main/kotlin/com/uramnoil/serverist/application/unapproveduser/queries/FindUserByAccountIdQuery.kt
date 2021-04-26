package com.uramnoil.serverist.application.unapproveduser.queries

import com.uramnoil.serverist.application.unapproveduser.User

data class FindUserByAccountIdQueryDto(val id: String)

fun interface FindUserByAccountIdQuery {
    fun execute(dto: FindUserByAccountIdQueryDto)
}

data class FindUserByAccountIdQueryOutputPortDto(val user: User?)

fun interface FindUserByAccountIdQueryOutputPort {
    fun handle(dto: FindUserByAccountIdQueryOutputPortDto)
}
