package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface ValidateLoginService {
    suspend fun execute(accountIdOrEmail: String, password: String): User?
}