package com.uramnoil.serverist.application.auth

import com.benasher44.uuid.Uuid

fun interface WithdrawalUseCaseInputPortForClient {
    fun execute()
}

fun interface WithdrawalUseCaseInputPortForServer {
    fun execute(id: Uuid)
}

fun interface WithdrawalUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}