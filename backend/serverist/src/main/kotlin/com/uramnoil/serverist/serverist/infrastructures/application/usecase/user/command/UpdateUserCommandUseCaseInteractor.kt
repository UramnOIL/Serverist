package com.uramnoil.serverist.serverist.infrastructures.application.usecase.user.command

import com.uramnoil.serverist.common.domain.exception.UserNotFoundByIdException
import com.uramnoil.serverist.common.domain.models.kernel.UserId
import com.uramnoil.serverist.serverist.application.usecases.user.commands.UpdateUserCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.domain.models.user.AccountId
import com.uramnoil.serverist.serverist.domain.models.user.Description
import com.uramnoil.serverist.serverist.domain.models.user.Name
import com.uramnoil.serverist.serverist.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class UpdateUserCommandUseCaseInteractor(
    private val repository: UserRepository,
) : UpdateUserCommandUseCaseInputPort {
    override suspend fun execute(id: UUID, accountId: String, name: String, description: String): Result<Unit> {
        val user = repository.findById(UserId(id)).getOrElse {
            return Result.failure(it)
        }

        user ?: run {
            return Result.failure(UserNotFoundByIdException(id.toString()))
        }

        user.apply {
            this.accountId = AccountId(accountId)
            this.name = Name(name)
            this.description = Description(description)
        }

        return repository.update(user)
    }
}
