package com.uramnoil.serverist.application.unapproved.queries

data class FindUserByAccountIdQueryDto(val id: String)

interface FindUserByAccountIdQuery {
    fun execute(dto: FindUserByAccountIdQueryDto)
}

data class FindUserByAccountIdQueryOutputPortDto(val user: User?)

interface FindUserByAccountIdQueryOutputPort {
    fun handle()
}
