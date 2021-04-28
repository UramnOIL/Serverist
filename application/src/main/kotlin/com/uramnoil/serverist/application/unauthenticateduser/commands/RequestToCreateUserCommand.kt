package com.uramnoil.serverist.application.unauthenticateduser.commands

import com.uramnoil.serverist.application.unauthenticateduser.User

data class RequestToCreateUserCommandDto(
    val accountId: String,
    val email: String,
    val password: String,
    val name: String
)

fun interface OfferToCreateUserCommand {
    fun execute(dto: RequestToCreateUserCommandDto)
}

data class RequestToCreateUserCommandOutputPortDto(val user: User)

enum class RequestToCreateUserCommandError {
    ApprovedUserExists,
    AlreadyRequested,
}

data class OfferToCreateUserCommandOutputPortErrorDto(val error: RequestToCreateUserCommandError)

interface OfferToCreateUserCommandOutputPort {
    fun handle(dto: RequestToCreateUserCommandOutputPortDto)

    fun error(dto: OfferToCreateUserCommandOutputPortErrorDto)
}