package com.uramnoil.serverist.application.authenticated

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
    fun handle(result: Result<Unit>)
}