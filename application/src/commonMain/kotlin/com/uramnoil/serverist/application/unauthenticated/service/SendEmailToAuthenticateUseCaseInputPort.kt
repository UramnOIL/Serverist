package com.uramnoil.serverist.application.unauthenticated.service

import com.benasher44.uuid.Uuid


/**
 *
 */
fun interface SendEmailToAuthenticateUseCaseInputPort {
    /**
     *
     */
    fun execute(email: String, activationCode: Uuid)
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