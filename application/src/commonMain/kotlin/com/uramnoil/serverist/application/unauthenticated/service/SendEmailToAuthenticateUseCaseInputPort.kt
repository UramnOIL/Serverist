package com.uramnoil.serverist.application.unauthenticated.service

import java.util.*


/**
 *
 */
fun interface SendEmailToAuthenticateUseCaseInputPort {
    /**
     *
     */
    fun execute(email: String, activationCode: UUID)
}

/**
 *
 */
fun interface SendEmailToAuthenticateUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Unit>)
}