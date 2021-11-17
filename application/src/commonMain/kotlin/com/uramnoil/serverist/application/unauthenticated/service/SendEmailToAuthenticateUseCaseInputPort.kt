package com.uramnoil.serverist.application.unauthenticated.service

import java.util.*


/**
 *
 */
interface SendEmailToAuthenticateUseCaseInputPort {
    /**
     *
     */
    fun execute(email: String, activationCode: UUID)
}

/**
 *
 */
interface SendEmailToAuthenticateUseCaseOutputPort {
    /**
     *
     */
    fun execute(result: Result<Unit>)
}