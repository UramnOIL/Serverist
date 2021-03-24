package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.application.usecases.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.usecases.user.commands.CreateUserDto
import com.uramnoil.serverist.domain.service.services.user.CreateUserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ExposedCreateUserCommand(private val service: CreateUserService, context: CoroutineContext) :
    CreateUserCommand, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: CreateUserDto): Unit {
        launch {
            service.newAsync(dto.name, dto.description).await()
        }
    }
}