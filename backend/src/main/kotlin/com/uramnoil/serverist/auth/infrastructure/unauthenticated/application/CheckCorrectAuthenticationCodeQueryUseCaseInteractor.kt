package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application

import com.uramnoil.serverist.auth.application.unauthenticated.queries.CheckCorrectAuthenticationCodeQueryUseCaseInputPort
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import com.uramnoil.serverist.domain.common.exception.NotFoundException
import java.util.*

class CheckCorrectAuthenticationCodeQueryUseCaseInteractor(private val repository: UserRepository) :
    CheckCorrectAuthenticationCodeQueryUseCaseInputPort {
    override suspend fun execute(id: UUID, code: String): Result<Boolean> {
        // Try find
        val result = repository.findById(Id(id))

        // Repository error
        val user = result.getOrElse {
            return Result.failure(it)
        }

        // If not found
        user ?: run {
            return Result.failure(NotFoundException("token"))
        }

        return Result.success(user.authenticationCode.value == code)
    }
}