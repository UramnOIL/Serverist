package com.uramnoil.serverist.application.auth

import com.benasher44.uuid.Uuid

fun interface ActivateUseCaseInputPort {
    fun execute(code: Uuid)
}

fun interface ActivateUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}