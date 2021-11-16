package com.uramnoil.serverist.auth.application.unauthenticated.queries

import java.util.*

interface FindUserByIdQueryUseCaseInputPort {
    suspend fun execute(id: UUID): Result<User?>
}