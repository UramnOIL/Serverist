package com.uramnoil.serverist.application.unapproved.queries

data class FindUserByIdQueryDto(val id: String)

interface FindUserByIdQuery {
    fun execute(dto: FindUserByIdQueryDto)
}

data class FindUserByIdQueryOutputPortDto(val user: User?)

interface FindUserByIdQueryOutputPort {
    fun handle()
}
