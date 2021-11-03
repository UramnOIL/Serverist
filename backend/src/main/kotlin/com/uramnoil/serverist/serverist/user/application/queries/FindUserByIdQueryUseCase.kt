package com.uramnoil.serverist.serverist.user.application.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.serverist.user.application.User

interface FindUserByIdQueryUseCaseInputPort {
    suspend fun execute(id: Uuid): Result<User?>
}