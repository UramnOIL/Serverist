package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

data class FindUserByAccountIdQueryDto(val accountId: String)

interface FindUserByAccountIdQuery {
    suspend fun execute(dto: FindUserByAccountIdQueryDto): User?
}