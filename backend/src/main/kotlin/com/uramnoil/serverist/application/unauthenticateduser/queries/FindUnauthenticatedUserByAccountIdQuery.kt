package com.uramnoil.serverist.application.unauthenticateduser.queries

import com.uramnoil.serverist.application.unauthenticateduser.User

data class FindUnauthenticatedUserByAccountIdQueryDto(val id: String)

interface FindUnauthenticatedUserByAccountIdQuery {
    suspend fun execute(dto: FindUnauthenticatedUserByAccountIdQueryDto): User?
}