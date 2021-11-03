package com.uramnoil.serverist.auth.application.unauthenticated

import com.benasher44.uuid.Uuid


interface TryAuthEmailUseCaseInputPort {
    suspend fun execute(token: Uuid): Result<Unit>
}