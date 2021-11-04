package com.uramnoil.serverist.auth.infrastructure.authenticated.application.commands

import com.uramnoil.serverist.auth.application.authenticated.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.domain.auth.authenticated.models.User
import com.uramnoil.serverist.domain.auth.authenticated.repositories.UserRepository
import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService
import com.uramnoil.serverist.domain.common.user.Id
import java.util.*

class CreateUserCommandUseCaseInteractor(
    private val repository: UserRepository,
    private val hashPasswordService: HashPasswordService
) : CreateUserCommandUseCaseInputPort {
    override suspend fun execute(email: String, password: String): Result<UUID> {
        val id = UUID.randomUUID()
        val newResult = User.new(Id(id), Email(email), hashPasswordService.hash(Password(password)))

        val newUser = newResult.getOrElse {
            return Result.failure(it)
        }

        return repository.insert(newUser).map { id }
    }
}