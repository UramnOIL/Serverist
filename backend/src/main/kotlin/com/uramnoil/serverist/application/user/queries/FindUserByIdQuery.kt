package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.kernel.User
import java.util.*

data class FindUserByIdQueryDto(val id: UUID)

interface FindUserByIdQuery {
    suspend fun execute(dto: FindUserByIdQueryDto): User
}