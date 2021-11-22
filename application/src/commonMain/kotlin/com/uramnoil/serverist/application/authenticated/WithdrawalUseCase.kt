package com.uramnoil.serverist.application.authenticated

fun interface WithdrawalUseCaseInputPort {
    fun execute()
}

fun interface WithdrawalUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}