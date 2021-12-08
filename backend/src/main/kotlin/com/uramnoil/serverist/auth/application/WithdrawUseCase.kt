package com.uramnoil.serverist.auth.application

import com.benasher44.uuid.Uuid


fun interface WithdrawUseCaseInputPort {
    fun execute(id: Uuid)
}

fun interface WithdrawUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}