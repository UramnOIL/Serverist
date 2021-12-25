package com.uramnoil.serverist.auth.application

fun interface ActivateUseCaseInputPort {
    fun execute(email: String, activationCode: Int)
}

fun interface ActivateUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}