package com.uramnoil.serverist.application.unapproveduser.commands

import com.uramnoil.serverist.application.unapproveduser.User

data class OfferToCreateUserCommandDto(val accountId: String, val email: String, val password: String, val name: String)

fun interface OfferToCreateUserCommand {
    fun execute(dto: OfferToCreateUserCommandDto)
}

data class OfferToCreateUserCommandOutputPortDto(val user: User)

enum class OfferToCreateUserCommandError {
    ApprovedUserExists,
    AlreadyRequested,
}

data class OfferToCreateUserCommandOutputPortErrorDto(val error: OfferToCreateUserCommandError)

interface OfferToCreateUserCommandOutputPort {
    fun handle(dto: OfferToCreateUserCommandOutputPortDto)

    fun error(dto: OfferToCreateUserCommandOutputPortErrorDto)
}