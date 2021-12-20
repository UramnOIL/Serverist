package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.application.server.CreateServerUseCaseInput
import com.uramnoil.serverist.application.server.CreateServerUseCaseInputPort

class CreateServerController(private val createServerUseCaseInputPort: CreateServerUseCaseInputPort) {
    fun createServer(name: String, host: String?, port: UShort?, description: String) {
        createServerUseCaseInputPort.execute(CreateServerUseCaseInput(name, host, port, description))
    }
}