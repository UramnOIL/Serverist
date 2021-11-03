package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application

import com.uramnoil.serverist.auth.application.unauthenticated.TryAuthEmailUseCaseInputPort
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import com.uramnoil.serverist.domain.common.exception.NotFoundException
import java.util.*

class TryAuthEmailUseCaseInteractor(private val repository: UserRepository) : TryAuthEmailUseCaseInputPort {
    override suspend fun execute(token: UUID): Result<Unit> {
        // Try find
        val result = repository.findById(Id(token))

        // Repository error
        val user = result.getOrElse {
            return Result.failure(it)
        }

        // If not found
        user ?: run {
            return Result.failure(NotFoundException("token"))
        }

        return Result.success(Unit)
    }
}