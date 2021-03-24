package com.uramnoil.serverist.controllers

import com.uramnoil.serverist.application.usecases.server.commands.*
import com.uramnoil.serverist.application.usecases.server.queries.FindServerByIdQuery

class ServerController(
    private val createServerCommand: CreateServerCommand,
    private val updateServerCommand: UpdateServerCommand,
    private val deleteServerCommand: DeleteServerCommand,
    private val findServerByIdQuery: FindServerByIdQuery
) {
    fun create(dto: CreateServerDto) {
        createServerCommand.execute(dto)
    }

    fun update(dto: UpdateServerDto) {
        updateServerCommand.execute(dto)
    }

    fun delete(dto: DeleteServerDto) {
        deleteServerCommand.execute(dto)
    }
}