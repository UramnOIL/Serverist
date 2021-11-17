package com.uramnoil.serverist.auth.infrastructure.authenticated.application.commands

import com.uramnoil.serverist.application.authenticated.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.authenticated.commands.CreateUserCommandUseCaseOutputPort
import com.uramnoil.serverist.domain.auth.authenticated.models.User
import com.uramnoil.serverist.domain.auth.authenticated.repositories.UserRepository
import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService
import com.uramnoil.serverist.domain.common.user.Id
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class CreateUserCommandUseCaseInteractor(
    private val repository: UserRepository,
    private val hashPasswordService: HashPasswordService,
    private val outputPort: CreateUserCommandUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : CreateUserCommandUseCaseInputPort {
    override fun execute(email: String, password: String) {
        val id = UUID.randomUUID()
        val newResult = User.new(Id(id), Email(email), hashPasswordService.hash(Password(password)))

        val newUser = newResult.getOrElse {
            outputPort.handle(Result.failure(it))
            return
        }

        CoroutineScope(coroutineContext).launch {
            outputPort.handle(repository.insert(newUser).map { id })
        }
    }
}