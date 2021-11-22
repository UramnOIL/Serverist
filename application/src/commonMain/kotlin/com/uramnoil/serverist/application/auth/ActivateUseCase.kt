package com.uramnoil.serverist.application.auth

import java.util.*

fun interface ActivateUseCaseInputPort {
    fun execute(code: UUID)
}

fun interface ActivateUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}