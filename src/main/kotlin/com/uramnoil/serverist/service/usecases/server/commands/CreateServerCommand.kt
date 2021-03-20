package com.uramnoil.serverist.service.usecases.server.commands

interface CreateServerCommand {
    data class CreateServerDto(val name: String, val address: String, val port: Int, val description: String)

    fun execute(dto: CreateServerDto)
}