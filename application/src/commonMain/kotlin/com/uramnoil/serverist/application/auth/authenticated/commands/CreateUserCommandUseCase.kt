package com.uramnoil.serverist.application.auth.authenticated.commands

interface CreateUserCommandUseCaseInputPort {
    fun execute(email: String, password: String)
}

fun interface CreateUserCommandUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}