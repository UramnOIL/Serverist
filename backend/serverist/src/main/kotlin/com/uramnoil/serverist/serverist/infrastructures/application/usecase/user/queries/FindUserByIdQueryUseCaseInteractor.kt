package com.uramnoil.serverist.serverist.infrastructures.application.usecase.user.queries

import com.uramnoil.serverist.common.domain.models.kernel.UserId
import com.uramnoil.serverist.serverist.application.usecases.user.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.domain.repositories.UserRepository
import java.util.UUID

class FindUserByIdQueryUseCaseInteractor(
    private val repository: UserRepository,
) : FindUserByIdQueryUseCaseInputPort {
    override suspend fun execute(id: UUID) {
        return repository.findById(UserId(id)).map { it?.toApplicationUser() }
    }
}
