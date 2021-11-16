package com.uramnoil.serverist.serverist.infrastructure.application.user.queries

import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.repositories.UserRepository
import com.uramnoil.serverist.serverist.application.user.User
import com.uramnoil.serverist.serverist.application.user.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.user.infrastructure.application.toApplicationUser
import java.util.*

class FindUserByIdQueryUseCaseInteractor(private val repository: UserRepository) : FindUserByIdQueryUseCaseInputPort {
    override suspend fun execute(id: UUID): Result<User?> {
        return repository.findById(Id(id)).map { it?.toApplicationUser() }
    }
}