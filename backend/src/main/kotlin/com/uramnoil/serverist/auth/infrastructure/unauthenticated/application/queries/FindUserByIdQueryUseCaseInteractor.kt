package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.auth.application.unauthenticated.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.queries.User
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.toApplication
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository

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