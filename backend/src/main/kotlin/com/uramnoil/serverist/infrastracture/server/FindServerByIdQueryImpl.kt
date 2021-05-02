package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.queries.FindServerByIdDto
import com.uramnoil.serverist.application.server.queries.FindServerByIdQuery
import com.uramnoil.serverist.domain.models.server.Id
import com.uramnoil.serverist.domain.repositories.ServerRepository
import java.util.*

class FindServerByIdQueryImpl(
    private val repository: ServerRepository,
) : FindServerByIdQuery {
    override suspend fun execute(dto: FindServerByIdDto): Server? {
        val server = repository.findById(Id(UUID.fromString(dto.id)))

        return server?.run {
            Server(
                id.value,
                ownerId.value,
                name.value,
                address.value,
                port.value,
                description.value
            )
        }
    }
}