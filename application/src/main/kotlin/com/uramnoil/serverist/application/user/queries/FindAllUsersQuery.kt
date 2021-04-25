package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

fun interface FindAllUsersQuery {
    fun execute()
}

fun interface FindAllUsersOutputPort {
    fun handle(dto: FindAllUsersOutputPortDto)
}

data class FindAllUsersOutputPortDto(val users: List<User>)