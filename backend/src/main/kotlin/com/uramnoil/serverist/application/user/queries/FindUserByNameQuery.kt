package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.kernel.User

data class FindUserByNameDto(val name: String, val serversLimit: Long)

interface FindUserByNameQuery {
    suspend fun execute(dto: FindUserByNameDto): User?
}