package com.uramnoil.serverist.auth.application.unauthenticated.queries

import java.util.*

interface CheckCorrectAuthenticationCodeQueryUseCaseInputPort {
    suspend fun execute(id: UUID, code: String): Result<Boolean>
}