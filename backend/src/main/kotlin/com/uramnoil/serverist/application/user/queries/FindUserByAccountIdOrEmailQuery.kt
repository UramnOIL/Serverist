package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

data class FindUserByAccountIdOrEmailQueryDto(val accountIdOrEmail: String)

interface FindUserByAccountIdOrEmailQuery {

    interface FindUserByAccountIdOrEmailQuery {
        suspend fun execute(dto: FindUserByAccountIdOrEmailQueryDto): User?
    }
}