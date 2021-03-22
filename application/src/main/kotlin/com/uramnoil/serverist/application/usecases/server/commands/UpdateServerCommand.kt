package com.uramnoil.serverist.application.usecases.server.commands

interface UpdateServerCommand {
    fun execute(dto: UpdateServerDto)
}


data class UpdateServerDto(val id: String, val address: String, val port: Int, val description: String)