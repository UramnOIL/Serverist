package com.uramnoil.serverist.application.auth.unauthenticated.commands

interface CreateUserCommandUseCaseInputPort {
    fun execute(
        email: String,
        password: String
    )
}

fun interface CreateUserCommandUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}