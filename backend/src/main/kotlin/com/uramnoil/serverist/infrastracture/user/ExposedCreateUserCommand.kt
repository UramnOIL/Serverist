package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.user.commands.CreateUserDto
import com.uramnoil.serverist.domain.models.user.AccountId
import com.uramnoil.serverist.domain.models.user.Description
import com.uramnoil.serverist.domain.models.user.Name
import com.uramnoil.serverist.domain.services.user.CreateUserService
import com.uramnoil.serverist.domain.services.user.Email
import com.uramnoil.serverist.domain.services.user.HashedPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ExposedCreateUserCommand(
    private val createUserService: CreateUserService,
    context: CoroutineContext
) : CreateUserCommand, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: CreateUserDto) {
        launch {
            createUserService.new(
                AccountId(dto.accountId),
                Email(dto.email),
                HashedPassword(dto.hashedPassword),
                Name(dto.name),
                Description(dto.description)
            )
        }
    }
}