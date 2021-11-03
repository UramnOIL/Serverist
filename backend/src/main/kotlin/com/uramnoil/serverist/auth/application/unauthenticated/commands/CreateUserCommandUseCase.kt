package com.uramnoil.serverist.auth.application.unauthenticated.commands

import kotlinx.datetime.Instant

class AccountAlreadyExistsException : IllegalArgumentException()
class VerificationCodeHasAlreadyBeenSentException : IllegalArgumentException()

interface CreateUserCommandUseCaseInputPort {
    suspend fun execute(
        email: String,
        password: String,
        authenticationCode: String,
        expiredAt: Instant
    ): Result<Unit>
}