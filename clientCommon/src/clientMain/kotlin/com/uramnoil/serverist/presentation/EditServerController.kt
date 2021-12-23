package com.uramnoil.serverist.presentation

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.DeleteServerUseCaseInput
import com.uramnoil.serverist.application.server.DeleteServerUseCaseInputPort
import com.uramnoil.serverist.application.server.FindServerByIdUseCaseInput
import com.uramnoil.serverist.application.server.FindServerByIdUseCaseInputPort
import com.uramnoil.serverist.application.server.UpdateServerUseCaseInput
import com.uramnoil.serverist.application.server.UpdateServerUseCaseInputPort

class EditServerController(
    private val updateServerUseCaseInputPort: UpdateServerUseCaseInputPort,
    private val deleteServerUseCaseInputPort: DeleteServerUseCaseInputPort,
    private val findServerByIdUseCaseInputPort: FindServerByIdUseCaseInputPort,
) {
    fun updateServer(id: Uuid, name: String, host: String?, port: UShort?, description: String) {
        updateServerUseCaseInputPort.execute(UpdateServerUseCaseInput(id, name, host, port, description))
    }

    fun deleteServer(id: Uuid) {
        deleteServerUseCaseInputPort.execute(DeleteServerUseCaseInput(id))
    }

    fun findServer(id: Uuid) {
        findServerByIdUseCaseInputPort.execute(FindServerByIdUseCaseInput(id))
    }
}