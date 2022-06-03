package com.uramnoil.serverist.serverist.infrastructures.application.usecase.user.command

import com.uramnoil.serverist.common.domain.models.kernel.UserId
import com.uramnoil.serverist.serverist.application.usecases.user.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.domain.models.server.Id
import com.uramnoil.serverist.serverist.domain.models.user.AccountId
import com.uramnoil.serverist.serverist.domain.models.user.Description
import com.uramnoil.serverist.serverist.domain.models.user.Name
import com.uramnoil.serverist.serverist.domain.models.user.User
import com.uramnoil.serverist.serverist.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class CreateUserCommandUseCaseInteractor(
    private val repository: UserRepository,
) : CreateUserCommandUseCaseInputPort {
    override suspend fun execute(
        id: UUID,
        accountId: String,
        name: String,
        description: String
    ): Result<UUID> {
            val userResult = User.new(
                UserId(id),
                AccountId(accountId),
                Name(name),
                Description(description)
            )
            val result = userResult.mapCatching {
                repository.insert(it).getOrThrow()
                it.toApplicationUser()
            }
            return result.map { it.id }
        }
    }
}
