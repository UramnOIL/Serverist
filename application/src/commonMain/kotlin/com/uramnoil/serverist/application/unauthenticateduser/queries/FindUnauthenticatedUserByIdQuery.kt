package com.uramnoil.serverist.application.unauthenticateduser.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser

interface FindUnauthenticatedUserByIdQuery {
    suspend fun execute(id: Uuid): UnauthenticatedUser?
}