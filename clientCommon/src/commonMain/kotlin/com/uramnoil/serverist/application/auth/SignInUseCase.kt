package com.uramnoil.serverist.application.auth

import com.benasher44.uuid.Uuid

/**
 *
 */
fun interface SignInUseCaseInputPort {
    /**
     *
     */
    fun execute(email: String, password: String)
}

/**
 *
 */
/**
 *
 */
data class SignInUseCaseOutput(private val result: Result<Uuid>)

/**
 *
 */
fun interface SignInUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: SignInUseCaseOutput)
}