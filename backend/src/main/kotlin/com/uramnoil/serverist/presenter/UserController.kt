package com.uramnoil.serverist.presenter

import com.uramnoil.serverist.serverist.application.user.User
import com.uramnoil.serverist.serverist.application.user.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.user.commands.CreateUserCommandUseCaseOutputPort
import com.uramnoil.serverist.serverist.application.user.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.user.commands.DeleteUserCommandUseCaseOutputPort
import com.uramnoil.serverist.serverist.application.user.commands.UpdateUserCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.user.commands.UpdateUserCommandUseCaseOutputPort
import com.uramnoil.serverist.serverist.application.user.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.user.queries.FindUserByIdQueryUseCaseOutputPort
import kotlinx.coroutines.currentCoroutineContext
import java.util.UUID
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserController(
    private val createUserCommandUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: CreateUserCommandUseCaseOutputPort) -> CreateUserCommandUseCaseInputPort,
    private val updateUserCommandUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: UpdateUserCommandUseCaseOutputPort) -> UpdateUserCommandUseCaseInputPort,
    private val deleteUserCommandUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: DeleteUserCommandUseCaseOutputPort) -> DeleteUserCommandUseCaseInputPort,
    private val findUserByIdQueryUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: FindUserByIdQueryUseCaseOutputPort) -> FindUserByIdQueryUseCaseInputPort,
) {
    suspend fun create(id: UUID, accountId: String, name: String, description: String): Result<UUID> {
        val context = currentCoroutineContext()
        return suspendCoroutine {
            val outputPort = CreateUserCommandUseCaseOutputPort { result ->
                it.resume(result)
            }
            createUserCommandUseCaseInputPortFactory(context, outputPort).execute(id, accountId, name, description)
        }
    }

    suspend fun update(id: UUID, accountId: String, name: String, description: String): Result<Unit> {
        val context = currentCoroutineContext()
        return suspendCoroutine {
            val outputPort = UpdateUserCommandUseCaseOutputPort { result ->
                it.resume(result)
            }
            updateUserCommandUseCaseInputPortFactory(context, outputPort).execute(
                id,
                accountId,
                name,
                description
            )
        }
    }

    suspend fun delete(id: UUID): Result<Unit> {
        val context = currentCoroutineContext()
        return suspendCoroutine {
            val outputPort = DeleteUserCommandUseCaseOutputPort { result ->
                it.resume(result)
            }
            deleteUserCommandUseCaseInputPortFactory(context, outputPort).execute(id)
        }
    }

    suspend fun findById(id: UUID): Result<User?> {
        val context = currentCoroutineContext()
        return suspendCoroutine {
            val outputPort = FindUserByIdQueryUseCaseOutputPort { result ->
                it.resume(result)
            }
            findUserByIdQueryUseCaseInputPortFactory(context, outputPort).execute(id)
        }
    }
}
