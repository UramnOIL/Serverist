package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

data class FindUserByNameDto(val name: String)

interface FindUserByNameQuery {
    suspend fun execute(dto: FindUserByNameDto): User?
}