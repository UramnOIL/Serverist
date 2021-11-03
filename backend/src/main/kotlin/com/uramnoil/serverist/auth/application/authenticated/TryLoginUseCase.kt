package com.uramnoil.serverist.auth.application.authenticated

import com.benasher44.uuid.Uuid


interface TryLoginUseCaseInputPort {
    suspend fun execute(mail: String, password: String): Result<Uuid?>
}