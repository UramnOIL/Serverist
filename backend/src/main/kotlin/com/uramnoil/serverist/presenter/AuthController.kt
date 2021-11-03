package com.uramnoil.serverist.presenter

import com.uramnoil.serverist.auth.application.authenticated.TryLoginUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.commands.CreateUserCommandUseCaseInputPort
import java.util.*

class AuthController(
    private val createUserCommandUseCaseInputPort: CreateUserCommandUseCaseInputPort,
    private val tryLoginUseCaseInputPort: TryLoginUseCaseInputPort,
) {
    suspend fun signUp(email: String, password: String): Result<Unit> {
        return createUserCommandUseCaseInputPort.execute(email, password)
    }

    suspend fun login(email: String, password: String): Result<UUID?> {
        return tryLoginUseCaseInputPort.execute(email, password)
    }

    suspend fun activate(token: String): Result<Unit> {
        TODO("後で実装")
    }
}