package com.uramnoil.serverist.infrastracture.unauthenticateduser

import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser
import com.uramnoil.serverist.application.unauthenticateduser.queries.FindUnauthenticatedUserByIdQuery
import com.uramnoil.serverist.domain.unauthenticateduser.models.Id
import com.uramnoil.serverist.domain.unauthenticateduser.repositories.UnauthenticatedUserRepository
import java.util.*

class FindUnauthenticatedUserByIdQueryImpl(private val repository: UnauthenticatedUserRepository) :
    FindUnauthenticatedUserByIdQuery {
    override suspend fun execute(id: UUID): UnauthenticatedUser? {
        return repository.findById(Id(id))?.toApplication()
    }
}