package com.uramnoil.serverist.auth.application

import com.benasher44.uuid.Uuid

/**
 *
 */
fun interface SingInUseCaseInputPort {
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