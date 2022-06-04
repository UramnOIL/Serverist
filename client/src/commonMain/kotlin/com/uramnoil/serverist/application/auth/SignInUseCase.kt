package com.uramnoil.serverist.application.auth

import com.benasher44.uuid.Uuid

/**
 *
 */
data class SignInUseCaseInput(val email: String, val password: String)

/**
 *
 */
fun interface SignInUseCaseInputPort {
    /**
     *
     */
    fun execute(input: SignInUseCaseInput)
}

/**
 *
 */
data class SignInUseCaseOutput(val result: Result<Uuid>)

/**
 *
 */
fun interface SignInUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: SignInUseCaseOutput)
}
