package com.uramnoil.serverist.auth.application.authenticated.commands

import java.util.*

interface DeleteUserCommandUseCaseInputPort {
    suspend fun execute(id: UUID): Result<Unit>
}