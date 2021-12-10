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
fun interface SignInUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Uuid>)
}