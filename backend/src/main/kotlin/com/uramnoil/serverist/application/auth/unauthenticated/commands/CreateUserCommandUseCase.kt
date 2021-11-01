package com.uramnoil.serverist.application.auth.unauthenticated.commands

interface CreateUserCommandUseCaseInputPort {
    suspend fun execute(
        email: String,
        password: String
    ): Result<Unit>
}