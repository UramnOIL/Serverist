package com.uramnoil.serverist.application.auth.unauthenticated.commands

interface CreateUnauthenticatedUserCommandUseCaseInputPort {
    fun execute(
        email: String,
        password: String
    )
}

fun interface CreateUnauthenticatedUserCommandUseCaseOutputPort {
    fun handle(result: Result<Unit>)
}