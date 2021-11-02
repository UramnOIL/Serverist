package com.uramnoil.serverist.koin.application

import com.uramnoil.serverist.application.user.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.user.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.user.commands.UpdateUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.user.queries.FindAllUsersQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.queries.FindUserByNameQueryUseCaseInputPort
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.infrastructure.user.command.CreateUserCommandUseCaseInteractor
import com.uramnoil.serverist.infrastructure.user.command.DeleteUserCommandUseCaseInteractor
import com.uramnoil.serverist.infrastructure.user.command.UpdateUserCommandUseCaseInteractor
import com.uramnoil.serverist.infrastructure.user.queries.ExposedFindAllUsersQueryUseCaseInteractor
import com.uramnoil.serverist.infrastructure.user.queries.ExposedFindUserByAccountIdQueryUseCaseInteractor
import com.uramnoil.serverist.infrastructure.user.queries.ExposedFindUserByNameQueryUseCaseInteractor
import io.ktor.application.*
import org.koin.dsl.module

fun Application.buildUserApplicationDI(
    userRepository: UserRepository,
) = module {
    single<CreateUserCommandUseCaseInputPort> {
        CreateUserCommandUseCaseInteractor(userRepository)
    }

    single<DeleteUserCommandUseCaseInputPort> {
        DeleteUserCommandUseCaseInteractor(userRepository)
    }

    single<UpdateUserCommandUseCaseInputPort> {
        UpdateUserCommandUseCaseInteractor(userRepository)
    }

    single<FindAllUsersQueryUseCaseInputPort> {
        ExposedFindAllUsersQueryUseCaseInteractor()
    }

    single<FindUserByAccountIdQueryUseCaseInputPort> {
        ExposedFindUserByAccountIdQueryUseCaseInteractor()
    }

    single<FindUserByNameQueryUseCaseInputPort> {
        ExposedFindUserByNameQueryUseCaseInteractor()
    }
}