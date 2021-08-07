package com.uramnoil.serverist.application.unauthenticateduser.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser

actual interface FindUnauthenticatedUserByIdQueryInputPort {
    fun execute(id: Uuid): UnauthenticatedUser?
}

interface FindUnauthenticatedUserByIdQueryOutputPort {
    fun handle(result: Result<UnauthenticatedUser?>)
}