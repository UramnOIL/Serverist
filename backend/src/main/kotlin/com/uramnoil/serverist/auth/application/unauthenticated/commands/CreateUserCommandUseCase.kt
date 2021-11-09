package com.uramnoil.serverist.auth.application.unauthenticated.commands

import java.util.*

class AccountAlreadyExistsException : IllegalArgumentException()
class VerificationCodeHasAlreadyBeenSentException : IllegalArgumentException()

interface CreateUserCommandUseCaseInputPort {
    suspend fun execute(
        email: String,
        password: String,
        authenticationCode: UUID,
    ): Result<UUID>
}