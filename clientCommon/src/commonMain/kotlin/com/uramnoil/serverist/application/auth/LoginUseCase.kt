package com.uramnoil.serverist.application.auth

import com.benasher44.uuid.Uuid

/**
 *
 */
fun interface LoginUseCaseInputPort {
    /**
     *
     */
    fun execute(email: String, password: String)
}

/**
 *
 */
fun interface LoginUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Uuid>)
}