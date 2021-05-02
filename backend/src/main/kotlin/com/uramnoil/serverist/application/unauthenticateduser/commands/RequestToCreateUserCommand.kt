package com.uramnoil.serverist.application.unauthenticateduser.commands

import com.uramnoil.serverist.application.unauthenticateduser.User

data class RequestToCreateUserCommandDto(
    val accountId: String,
    val email: String,
    val password: String,
    val name: String
)

interface OfferToCreateUserCommand {
    suspend fun execute(dto: RequestToCreateUserCommandDto): User
}