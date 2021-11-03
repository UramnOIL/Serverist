package com.uramnoil.serverist.auth.application.unauthenticated.commands

class AccountAlreadyExistsException : IllegalArgumentException()
class VerificationCodeHasAlreadyBeenSentException : IllegalArgumentException()

interface CreateUserCommandUseCaseInputPort {
    suspend fun execute(
        email: String,
        password: String
    ): Result<Unit>
}