package com.uramnoil.serverist.application.usecases.server.queries

interface FindServerByIdQuery {
    fun execute(dto: FindServerByIdDto)
}

class FindServerByIdDto(val id: String)


interface FindServerByIdOutputPort {
    fun handle(dto: FindServerByIdOutputPortDto)
}

data class FindServerByIdOutputPortDto(
    val id: String,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String?
)