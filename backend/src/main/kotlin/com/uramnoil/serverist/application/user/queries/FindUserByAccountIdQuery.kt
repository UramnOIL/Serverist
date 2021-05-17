package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.kernel.User

data class FindUserByAccountIdQueryDto(val accountId: String, val serversLimit: Long)

interface FindUserByAccountIdQuery {
    suspend fun execute(dto: FindUserByAccountIdQueryDto): User?
}