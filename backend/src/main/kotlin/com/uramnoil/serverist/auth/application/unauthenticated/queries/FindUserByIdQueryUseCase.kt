package com.uramnoil.serverist.auth.application.unauthenticated.queries

import com.benasher44.uuid.Uuid

interface FindUserByIdQueryUseCaseInputPort {
    suspend fun execute(id: Uuid): Result<User?>
}