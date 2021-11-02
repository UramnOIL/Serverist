package com.uramnoil.serverist.presenter

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.user.commands.UpdateUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.user.queries.FindUserByIdQueryUseCaseInputPort
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