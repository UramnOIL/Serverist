package com.uramnoil.serverist.application.auth.authenticated

import com.benasher44.uuid.Uuid


interface TryLoginUseCaseInputPort {
    fun execute(email: String, password: String)
}

fun interface TryLoginUseCaseOutputPort {
    fun handle(result: Result<Uuid?>)
}