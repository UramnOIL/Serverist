package com.uramnoil.serverist.application.unauthenticateduser.queries

import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser
import java.util.*

interface FindUnauthenticatedUserByIdQuery {
    suspend fun execute(id: UUID): UnauthenticatedUser?
}