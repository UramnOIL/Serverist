package com.uramnoil.serverist.infrastructure.auth.unauthenticated.application.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.auth.unauthenticated.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.application.auth.unauthenticated.queries.User
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import com.uramnoil.serverist.infrastructure.auth.unauthenticated.toApplication

class FindUserByIdQueryUseCaseInteractor(
    private val repository: UserRepository,
) :
    FindUserByIdQueryUseCaseInputPort {
    override suspend fun execute(id: Uuid): Result<User?> {
        val result = repository.findById(Id(id)).map {
            it?.toApplication()
        }
        return result
    }
}