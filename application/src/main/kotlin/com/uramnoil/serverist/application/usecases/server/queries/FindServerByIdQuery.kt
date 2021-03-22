package com.uramnoil.serverist.application.usecases.server.queries

interface FindServerByIdQuery {
    interface FindServerByIdOutputPort {
        data class FindServerByIdOutputPortDto(
            val id: String,
            val name: String,
            val address: String?,
            val port: Int?,
            val description: String?
        )

        fun handle(dto: FindServerByIdOutputPortDto)
    }

    class FindServerByIdDto(val id: String)

    fun execute(dto: FindServerByIdDto)
}