package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.user.commands.CreateUserDto
import com.uramnoil.serverist.domain.models.user.*
import com.uramnoil.serverist.domain.services.user.CreateUserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CreateUserCommandImpl(
    private val createUserService: CreateUserService,
    context: CoroutineContext
) : CreateUserCommand, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: CreateUserDto) {
        launch {
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
}