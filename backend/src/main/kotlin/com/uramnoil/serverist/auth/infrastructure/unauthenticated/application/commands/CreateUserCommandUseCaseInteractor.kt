package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.auth.application.unauthenticated.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.service.SendEmailToAuthenticateUseCase
import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService
import com.uramnoil.serverist.domain.auth.unauthenticated.models.ActivationCode
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import kotlinx.datetime.Instant
import kotlin.time.ExperimentalTime
import com.uramnoil.serverist.domain.auth.unauthenticated.models.User as DomainUser

class CreateUserCommandUseCaseInteractor(
    private val repository: UserRepository,
    private val hashPasswordService: HashPasswordService,
    private val sendEmailService: SendEmailToAuthenticateUseCase,
) :
    CreateUserCommandUseCaseInputPort {
    @OptIn(ExperimentalTime::class)
    override suspend fun execute(
        email: String,
        password: String,
        authenticationCode: String,
        expiredAt: Instant
    ): Result<Unit> {
        val hashedPassword = hashPasswordService.hash(Password(password))
        val newResult = DomainUser.new(
            id = Id(Uuid.randomUUID()),
            email = Email(email),
            hashedPassword = hashedPassword,
            activationCode = ActivationCode(authenticationCode),
        )

        val newUser = newResult.getOrElse {
            return Result.failure(it)
        }

        val insertResult = repository.insert(newUser)
        insertResult.getOrElse {
            return Result.failure(it)
        }

        sendEmailService.execute(email, authenticationCode).getOrElse {
            return Result.failure(it)
        }

        return insertResult
    }
}