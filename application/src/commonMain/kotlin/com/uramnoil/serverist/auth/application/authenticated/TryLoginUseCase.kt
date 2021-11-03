package com.uramnoil.serverist.auth.application.authenticated

import com.benasher44.uuid.Uuid


interface TryLoginUseCaseInputPort {
    fun execute(email: String, password: String)
}

fun interface TryLoginUseCaseOutputPort {
    fun handle(result: Result<Uuid?>)
}