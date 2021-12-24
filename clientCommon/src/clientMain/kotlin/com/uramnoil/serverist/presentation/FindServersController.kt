package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.FindAllServersUseCaseInput
import com.uramnoil.serverist.application.server.FindAllServersUseCaseInputPort

class FindServersController(
    private val findAllServersUseCaseInputPort: FindAllServersUseCaseInputPort
) {
    fun findAllServers() {
        findAllServersUseCaseInputPort.execute(FindAllServersUseCaseInput(100, 0, Sort.Asc, OrderBy.CreatedAt))
    }
}
