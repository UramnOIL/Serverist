package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

actual interface FindUserByAccountIdQuery {
    fun execute(accountId: String): User?
}

interface FindUserByAccountIdQueryOutputPort {
    fun handle(result: Result<User?>)
}