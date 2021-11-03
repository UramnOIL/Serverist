package com.uramnoil.serverist.auth.application.unauthenticated.queries

import com.uramnoil.serverist.domain.auth.kernel.model.Email

interface FindUserByEmailQueryUseCaseInputPort {
    suspend fun execute(email: Email): Result<User?>
}