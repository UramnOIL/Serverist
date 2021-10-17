package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface FindUserByAccountIdQueryUseCaseInputPort {
    fun execute(accountId: String)
}

interface FindUserByAccountIdQueryUseCaseOutputPort {
    fun handle(result: Result<User?>)
}