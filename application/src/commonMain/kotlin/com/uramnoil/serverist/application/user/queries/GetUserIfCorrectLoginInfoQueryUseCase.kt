package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface GetUserIfCorrectLoginInfoQueryUseCaseInputPort {
    fun execute(accountIdOrEmail: String, password: String)
}

interface GetUserIfCorrectLoginInfoQueryUseCaseOutputPort {
    fun handle(result: Result<User?>)
}