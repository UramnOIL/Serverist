package com.uramnoil.serverist.serverist.infrastructure.application.user.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.repositories.UserRepository
import com.uramnoil.serverist.serverist.application.user.User
import com.uramnoil.serverist.serverist.application.user.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.user.infrastructure.application.toApplicationUser

class FindUserByIdQueryUseCaseInteractor(private val repository: UserRepository) : FindUserByIdQueryUseCaseInputPort {
    override suspend fun execute(id: Uuid): Result<User?> {
        return repository.findById(Id(id)).map { it?.toApplicationUser() }
    }
}