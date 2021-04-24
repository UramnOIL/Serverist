package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.service.usecases.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.service.usecases.user.commands.CreateUserDto
import com.uramnoil.serverist.domain.models.user.AccountId
import com.uramnoil.serverist.domain.models.user.Description
import com.uramnoil.serverist.domain.models.user.Email
import com.uramnoil.serverist.domain.models.user.Name
import com.uramnoil.serverist.domain.services.user.CreateUserService
import com.uramnoil.serverist.domain.services.user.HashPasswordService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ExposedCreateUserCommand(
    private val createUserService: CreateUserService,
    private val hashPasswordService: HashPasswordService,
    context: CoroutineContext
) : CreateUserCommand, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: CreateUserDto) {
        launch {
            createUserService.newAsync(
                AccountId(dto.accountId),
                Email(dto.email),
                hashPasswordService.hash(dto.password),
                Name(dto.name),
                Description(dto.description)
            )
        }
    }
}