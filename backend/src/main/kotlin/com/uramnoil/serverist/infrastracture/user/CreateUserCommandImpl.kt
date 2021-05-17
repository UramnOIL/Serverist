package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.user.commands.CreateUserCommandDto
import com.uramnoil.serverist.domain.models.user.*
import com.uramnoil.serverist.domain.services.user.CreateUserService

class CreateUserCommandImpl(
    private val createUserService: CreateUserService
) : CreateUserCommand {
    override suspend fun execute(dto: CreateUserCommandDto) {
        dto.run {
            createUserService.new(
                AccountId(accountId),
                Email(email),
                HashedPassword(hashedPassword),
                Name(name),
                Description(description)
            )
        }
    }
}