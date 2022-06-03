package com.uramnoil.serverist.serverist.application.usecases.user.queries

import com.uramnoil.serverist.serverist.application.usecases.server.queries.UserDto
import java.util.*

/**
 *
 */
interface FindUserByIdQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(id: UUID): Result<UserDto?>
}

