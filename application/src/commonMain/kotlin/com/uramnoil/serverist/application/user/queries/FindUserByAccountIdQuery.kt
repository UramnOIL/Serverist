package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface FindUserByAccountIdQueryInputPort {
    fun execute(accountId: String)
}

interface FindUserByAccountIdQueryOutputPort {
    fun handle(result: Result<User?>)
}