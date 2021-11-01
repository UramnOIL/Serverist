package com.uramnoil.serverist.application.auth.authenticated.commands

interface CreateUserCommandUseCaseInputPort {
    suspend fun execute(email: String, password: String): Result<Unit>
}