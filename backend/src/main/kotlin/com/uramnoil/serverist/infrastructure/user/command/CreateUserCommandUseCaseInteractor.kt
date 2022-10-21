package com.uramnoil.serverist.infrastructure.user.command

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.user.commands.CreateUserCommandUseCaseOutputPort
import com.uramnoil.serverist.domain.common.user.UserId
import com.uramnoil.serverist.domain.user.models.AccountId
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Name
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.infrastracture.user.toApplicationUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import com.uramnoil.serverist.domain.user.models.User as DomainUser

class CreateUserCommandUseCaseInteractor(
    private val outputPort: CreateUserCommandUseCaseOutputPort,
    private val repository: UserRepository,
    parentContext: CoroutineContext,
) :
    CreateUserCommandUseCaseInputPort,
    CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(
        id: Uuid,
        accountId: String,
        name: String,
        description: String
    ) {
        launch {
            val userResult = DomainUser.new(
                UserId(id),
                AccountId(accountId),
                Name(name),
                Description(description)
            )
            val result = userResult.mapCatching {
                repository.insert(it).getOrThrow()
                it.toApplicationUser()
            }
            outputPort.handle(result)
        }
    }
}