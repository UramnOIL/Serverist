package com.uramnoil.serverist.application.unauthenticateduser

interface CreateUnauthenticatedUserCommandUseCaseInputPort {
    fun execute(
        accountId: String,
        email: String,
        password: String
    )
}

interface CreateUnauthenticatedUserCommandUseCaseOutputPort {
    fun handle(result: Result<UnauthenticatedUser>)
}