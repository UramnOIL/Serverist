package com.uramnoil.serverist.application.unapproved.queries

import com.uramnoil.serverist.application.unapproved.User

data class FindUserByAccountIdQueryDto(val id: String)

interface FindUserByAccountIdQuery {
    fun execute(dto: FindUserByAccountIdQueryDto)
}

data class FindUserByAccountIdQueryOutputPortDto(val user: User?)

interface FindUserByAccountIdQueryOutputPort {
    fun handle()
}
