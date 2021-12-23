package com.uramnoil.serverist.serverist.infrastructure.application.user.command

import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.models.user.AccountId
import com.uramnoil.serverist.domain.serverist.models.user.Description
import com.uramnoil.serverist.domain.serverist.models.user.Name
import com.uramnoil.serverist.domain.serverist.models.user.User
import com.uramnoil.serverist.domain.serverist.repositories.UserRepository
import com.uramnoil.serverist.serverist.application.user.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.user.commands.CreateUserCommandUseCaseOutputPort
import com.uramnoil.serverist.serverist.infrastructure.toApplicationUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class CreateUserCommandUseCaseInteractor(
    private val repository: UserRepository,
    private val outputPort: CreateUserCommandUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : CreateUserCommandUseCaseInputPort {
    override fun execute(
        id: UUID,
        accountId: String,
        name: String,
        description: String
    ) {
        CoroutineScope(coroutineContext).launch {
            val userResult = User.new(
                Id(id),
                AccountId(accountId),
                Name(name),
                Description(description)
            )
            val result = userResult.mapCatching {
                repository.insert(it).getOrThrow()
                it.toApplicationUser()
            }
            outputPort.handle(result.map { it.id })
            return@launch
        }
    }
}