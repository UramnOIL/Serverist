package com.uramnoil.serverist.application.usecases.user.queries

import java.util.*

interface FindAllUsersQuery {
    fun execute()
}

interface FindAllUsersOutputPort {
    fun handle(dto: FindAllUsersOutputPortDto)
}

data class UserDto(val id: UUID, val name: String, val description: String)

data class FindAllUsersOutputPortDto(val users: List<UserDto>)