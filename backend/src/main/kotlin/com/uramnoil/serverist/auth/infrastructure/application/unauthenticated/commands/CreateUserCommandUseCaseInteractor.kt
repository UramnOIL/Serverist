package com.uramnoil.serverist.auth.infrastructure.application.unauthenticated.commands

import com.uramnoil.serverist.application.unauthenticated.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.unauthenticated.commands.CreateUserCommandUseCaseOutputPort
import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService
import com.uramnoil.serverist.domain.auth.unauthenticated.models.ActivationCode
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.time.ExperimentalTime
import com.uramnoil.serverist.domain.auth.unauthenticated.models.User as DomainUser

class CreateUserCommandUseCaseInteractor(
    private val repository: UserRepository, private val hashPasswordService: HashPasswordService,
    private val outputPort: CreateUserCommandUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : CreateUserCommandUseCaseInputPort {
    @OptIn(ExperimentalTime::class)
    override fun execute(
        email: String,
        password: String,
        authenticationCode: UUID
    ) {
        CoroutineScope(coroutineContext).launch {
            val passwordResult = kotlin.runCatching {
                Password(password)
            }

            val newPassword = passwordResult.getOrElse {
                outputPort.handle(Result.failure(it))
                return@launch
            }

            val hashedPassword = hashPasswordService.hash(newPassword)
            val newResult = DomainUser.new(
                id = Id(UUID.randomUUID()),
                email = Email(email),
                hashedPassword = hashedPassword,
                activationCode = ActivationCode(authenticationCode),
            )

            val newUser = newResult.getOrElse {
                outputPort.handle(Result.failure(it))
                return@launch
            }

            val insertResult = repository.insert(newUser)
            outputPort.handle(insertResult.map { newUser.id.value })
            return@launch
        }
    }
}