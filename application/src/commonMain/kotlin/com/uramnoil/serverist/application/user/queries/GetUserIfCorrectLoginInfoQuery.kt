package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface GetUserIfCorrectLoginInfoQueryInputPort {
    fun execute(accountIdOrEmail: String, password: String)
}

interface GetUserIfCorrectLoginInfoQueryOutputPort {
    fun handle(result: Result<User?>)
}