package com.uramnoil.serverist.application.auth.unauthenticated.queries

import com.benasher44.uuid.Uuid

interface FindUnauthenticatedUserByIdQueryUseCaseInputPort {
    fun execute(id: Uuid)
}

fun interface FindUnauthenticatedUserByIdQueryUseCaseOutputPort {
    fun handle(result: Result<User?>)
}