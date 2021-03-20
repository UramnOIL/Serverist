package com.uramnoil.serverist.service.usecases.server

interface UpdateServerCommand {
    data class UpdateServerDto(val id: String, val address: String, val port: Int, val description: String)

    fun invoke(dto: UpdateServerDto)
}