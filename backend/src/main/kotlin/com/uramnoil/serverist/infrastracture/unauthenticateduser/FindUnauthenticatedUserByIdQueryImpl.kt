package com.uramnoil.serverist.infrastracture.unauthenticateduser

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser
import com.uramnoil.serverist.application.unauthenticateduser.queries.FindUnauthenticatedUserByIdQuery
import com.uramnoil.serverist.domain.unauthenticateduser.models.Id
import com.uramnoil.serverist.domain.unauthenticateduser.repositories.UnauthenticatedUserRepository

class FindUnauthenticatedUserByIdQueryImpl(private val repository: UnauthenticatedUserRepository) :
    FindUnauthenticatedUserByIdQuery {
    override suspend fun execute(id: Uuid): Result<UnauthenticatedUser?> = repository.findById(Id(id)).map {
        it?.toApplication()
    }
}