package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.application.server.queries.FindServerByIdDto
import com.uramnoil.serverist.application.server.queries.FindServerByIdOutputPort
import com.uramnoil.serverist.application.server.queries.FindServerByIdOutputPortDto
import com.uramnoil.serverist.application.server.queries.FindServerByIdQuery
import com.uramnoil.serverist.domain.models.server.Id
import com.uramnoil.serverist.domain.repositories.ServerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class FindServerByIdQueryImpl(
    private val repository: ServerRepository,
    private val outputPort: FindServerByIdOutputPort,
    context: CoroutineContext
) : FindServerByIdQuery, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: FindServerByIdDto) {
        launch {
            val server = repository.findById(Id(UUID.fromString(dto.id)))

            val outputPortDto = FindServerByIdOutputPortDto(
                server?.run {
                    Server(
                        id.value,
                        ownerId.value,
                        name.value,
                        address.value,
                        port.value,
                        description.value
                    )
                }
            )


            outputPort.handle(outputPortDto)
        }
    }
}