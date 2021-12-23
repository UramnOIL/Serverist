package com.uramnoil.serverist.auth.application

import com.benasher44.uuid.Uuid

fun interface ActivateUseCaseInputPort {
    fun execute(code: Uuid)
}

fun interface ActivateUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}
