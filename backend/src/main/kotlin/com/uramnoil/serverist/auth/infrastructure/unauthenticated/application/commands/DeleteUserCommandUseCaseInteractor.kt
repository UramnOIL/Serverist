package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.commands

import com.uramnoil.serverist.application.unauthenticated.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.unauthenticated.commands.DeleteUserCommandUseCaseOutputPort
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class DeleteUserCommandUseCaseInteractor(
    private val repository: UserRepository,
    private val outputPort: DeleteUserCommandUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : DeleteUserCommandUseCaseInputPort {
    override fun execute(id: UUID) {
        CoroutineScope(coroutineContext).launch {
            val findResult = repository.findById(Id(id)).mapCatching {
                it ?: throw IllegalArgumentException("id=${id}に一致するユーザーが見つかりませんでした。")
            }

            val deleteResult = findResult.mapCatching {
                repository.delete(it).getOrThrow()
            }

            outputPort.handle(deleteResult)
            return@launch
        }
    }
}