package com.uramnoil.serverist.infrastructure.auth.unauthenticated.application.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.auth.unauthenticated.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.auth.unauthenticated.commands.CreateUserCommandUseCaseOutputPort
import com.uramnoil.serverist.application.auth.unauthenticated.service.SendEmailToAuthenticateService
import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService
import com.uramnoil.serverist.domain.auth.unauthenticated.models.ExpiredAt
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import com.uramnoil.serverist.domain.auth.unauthenticated.models.User as DomainUser

class CreateUserCommandUseCaseInteractor(
    private val outputPort: CreateUserCommandUseCaseOutputPort,
    private val repository: UserRepository,
    private val hashPasswordService: HashPasswordService,
    private val sendEmailService: SendEmailToAuthenticateService,
    parentContext: CoroutineContext
) :
    CreateUserCommandUseCaseInputPort,
    CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    @OptIn(ExperimentalTime::class)
    override fun execute(email: String, password: String) {
        launch {
            val hashedPassword = hashPasswordService.hash(Password(password))
            val newResult = DomainUser.new(
                id = Id(Uuid.randomUUID()),
                email = Email(email),
                hashedPassword = hashedPassword,
                expiredAt = ExpiredAt(Clock.System.now().plus(Duration.minutes(30)))
            )
            val insertResult = newResult.mapCatching {
                repository.insert(it).getOrThrow()
            }

            insertResult.onSuccess {
                sendEmailService.execute(email, TODO("A token of url to verify user's email address"))
            }

            outputPort.handle(insertResult)
        }
    }
}