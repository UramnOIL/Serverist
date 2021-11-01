package com.uramnoil.serverist.application.auth.unauthenticated

import com.benasher44.uuid.Uuid


interface TryAuthEmailUseCaseInputPort {
    suspend fun execute(token: Uuid): Result<Unit>
}