package com.uramnoil.serverist.infrastracture.service

import com.uramnoil.serverist.application.service.usecases.server.queries.FindServerByIdDto
import com.uramnoil.serverist.application.service.usecases.server.queries.FindServerByIdOutputPort
import com.uramnoil.serverist.application.service.usecases.server.queries.FindServerByIdOutputPortDto
import com.uramnoil.serverist.application.service.usecases.server.queries.FindServerByIdQuery
import com.uramnoil.serverist.domain.repositories.ServerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ExposedFindServerByIdQuery(
    private val repository: ServerRepository,
    private val outputPort: FindServerByIdOutputPort,
    context: CoroutineContext
) : FindServerByIdQuery, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: FindServerByIdDto) {
        launch {
            outputPort.handle(dto.let {
                repository.findByIdAsync(com.uramnoil.serverist.domain.models.server.Id(dto.id)).await()?.run {
                    FindServerByIdOutputPortDto(
                        id.value,
                        owner.value,
                        name.value,
                        address.value,
                        port.value,
                        description.value
                    )
                }
            })
        }
    }
}