package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

actual interface ValidateLoginService {
    suspend fun execute(accountIdOrEmail: String, password: String): User?
}