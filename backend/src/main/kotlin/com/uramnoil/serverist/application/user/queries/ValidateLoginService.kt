package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.kernel.User

data class ValidateLoginServiceDto(val accountIdOrEmail: String, val password: String)

interface ValidateLoginService {
    suspend fun execute(dto: ValidateLoginServiceDto): User?
}