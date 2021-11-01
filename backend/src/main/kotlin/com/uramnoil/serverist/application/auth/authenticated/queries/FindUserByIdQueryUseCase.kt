package com.uramnoil.serverist.application.auth.authenticated.queries

import com.benasher44.uuid.Uuid

interface FindUserByIdQueryUseCaseInputPort {
    suspend fun execute(id: Uuid): Result<User?>
}
