package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

actual interface ValidateLoginService {
    fun execute(accountIdOrEmail: String, password: String): User?
}

interface ValidateLoginServiceOutputPort {
    fun handle(result: Result<User?>)
}