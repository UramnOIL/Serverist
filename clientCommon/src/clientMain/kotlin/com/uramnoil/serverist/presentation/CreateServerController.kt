package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.application.server.CreateServerUseCaseInput
import com.uramnoil.serverist.application.server.CreateServerUseCaseInputPort
import com.uramnoil.serverist.application.server.CreateServerUseCaseOutputPort
import kotlin.coroutines.CoroutineContext

class CreateServerController(
    coroutineContext: CoroutineContext,
    createServerUseCaseOutputPort: CreateServerUseCaseOutputPort
) {
    private val createServerUseCaseInputPort: CreateServerUseCaseInputPort = TODO("")

    fun createServer(name: String, host: String?, port: UShort?, description: String) {
        createServerUseCaseInputPort.execute(CreateServerUseCaseInput(name, host, port, description))
    }
}