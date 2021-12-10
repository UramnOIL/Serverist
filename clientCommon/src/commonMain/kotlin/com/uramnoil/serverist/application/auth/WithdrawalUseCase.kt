package com.uramnoil.serverist.application.auth

fun interface WithdrawalUseCaseInputPort {
    fun execute()
}

fun interface WithdrawalUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}