package com.uramnoil.serverist.auth.application.authenticated.queries

import java.util.*

interface FindUserByIdQueryUseCaseInputPort {
    suspend fun execute(id: UUID): Result<User?>
}
