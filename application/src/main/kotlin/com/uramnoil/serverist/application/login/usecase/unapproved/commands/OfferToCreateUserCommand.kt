package com.uramnoil.serverist.application.login.usecase.unapproved.commands

data class OfferToCreateUserCommandDto(val id: String, val email: String, val password: String, val name: String)

fun interface OfferToCreateUserCommand {
    fun execute(dto: OfferToCreateUserCommandDto)
}

data class OfferToCreateUserCommandOutputPortDto(val jwt: String)

enum class OfferToCreateUserCommandError {
    AlreadyCreated,
}

data class OfferToCreateUserCommandOutputPortErrorDto(val error: OfferToCreateUserCommandError)

interface OfferToCreateUserCommandOutputPort {
    fun handle(dto: OfferToCreateUserCommandOutputPortDto)

    fun error(dto: OfferToCreateUserCommandOutputPortErrorDto)
}