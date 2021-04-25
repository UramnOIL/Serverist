package com.uramnoil.serverist.application.user.queries

fun interface FindAllUsersQuery {
    fun execute()
}

fun interface FindAllUsersOutputPort {
    fun handle(dto: FindAllUsersOutputPortDto)
}

data class FindAllUsersOutputPortDto(val users: List<User>)