package com.uramnoil.serverist.application.unauthenticateduser.queries

import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser
import java.util.*

data class FindUnauthenticatedUserByIdQueryDto(val id: UUID)

interface FindUnauthenticatedUserByIdQuery {
    suspend fun execute(dto: FindUnauthenticatedUserByIdQueryDto): UnauthenticatedUser?
}