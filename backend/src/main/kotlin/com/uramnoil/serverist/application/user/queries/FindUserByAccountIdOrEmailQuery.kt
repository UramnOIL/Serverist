package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface FindUserByAccountIdOrEmailQuery {
    data class FindUserByAccountIdQueryDto(val accountIdOrEmail: String)

    interface FindUserByAccountIdQuery {
        suspend fun execute(dto: FindUserByAccountIdQueryDto): User?
    }
}