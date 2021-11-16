package com.uramnoil.serverist.serverist.application.user.commands

import java.util.*

interface DeleteUserCommandUseCaseInputPort {
    suspend fun execute(id: UUID): Result<Unit>
}