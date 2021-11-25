package com.uramnoil.serverist.application.unauthenticated.commands

import com.benasher44.uuid.Uuid

class AccountAlreadyExistsException : IllegalArgumentException()
class VerificationCodeHasAlreadyBeenSentException : IllegalArgumentException()

/**
 *
 */
interface CreateUserCommandUseCaseInputPort {
    /**
     *
     */
    fun execute(email: String, password: String, authenticationCode: Uuid)
}

/**
 *
 */
fun interface CreateUserCommandUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Uuid>)
}