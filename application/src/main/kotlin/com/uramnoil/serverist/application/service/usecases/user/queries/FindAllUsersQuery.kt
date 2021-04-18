package com.uramnoil.serverist.application.service.usecases.user.queries

fun interface FindAllUsersQuery {
    fun execute()
}

fun interface FindAllUsersOutputPort {
    fun handle(dto: FindAllUsersOutputPortDto)
}

data class UserDto(val id: String, val name: String, val description: String)

data class FindAllUsersOutputPortDto(val users: List<UserDto>)