package com.uramnoil.serverist.service.usecases.server

interface CreateServerCommand {
    data class Dto(val name: String, val address: String, val port: Int, val description: String)

    fun invoke(dto: Dto)
}