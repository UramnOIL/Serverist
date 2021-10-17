package com.uramnoil.serverist.application.unauthenticateduser.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser

interface FindUnauthenticatedUserByIdQueryInputPort {
    fun execute(id: Uuid)
}

interface FindUnauthenticatedUserByIdQueryOutputPort {
    fun handle(result: Result<UnauthenticatedUser?>)
}