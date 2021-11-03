package com.uramnoil.serverist.auth.application.authenticated.commands

interface CreateUserCommandUseCaseInputPort {
    suspend fun execute(email: String, password: String): Result<Unit>
}