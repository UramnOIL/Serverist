package com.uramnoil.serverist.presenter

import com.uramnoil.serverist.user.application.User
import com.uramnoil.serverist.user.application.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.user.application.commands.UpdateUserCommandUseCaseInputPort
import com.uramnoil.serverist.user.application.queries.FindUserByIdQueryUseCaseInputPort
import java.util.*

class UserController(
    private val findUserByIdQueryUseCaseInputPort: FindUserByIdQueryUseCaseInputPort,
    private val updateUserCommandUseCaseInputPort: UpdateUserCommandUseCaseInputPort,
    private val deleteUserCommandUseCaseInputPort: DeleteUserCommandUseCaseInputPort,
) {
    suspend fun findById(id: UUID): Result<User?> {
        return findUserByIdQueryUseCaseInputPort.execute(id)
    }

    suspend fun update(id: UUID, accountId: String, name: String, description: String): Result<Unit> {
        return updateUserCommandUseCaseInputPort.execute(id, accountId, name, description)
    }

    suspend fun delete(id: UUID): Result<Unit> {
        return deleteUserCommandUseCaseInputPort.execute(id)
    }
}