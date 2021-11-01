package com.uramnoil.serverist.application.auth.authenticated

import com.benasher44.uuid.Uuid


interface TryLoginUseCaseInputPort {
    suspend fun execute(accountIdOrEmail: String, password: String): Result<Uuid?>
}