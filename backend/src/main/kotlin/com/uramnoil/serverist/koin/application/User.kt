package com.uramnoil.serverist.koin.application

import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.user.application.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.user.application.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.user.application.commands.UpdateUserCommandUseCaseInputPort
import com.uramnoil.serverist.user.application.queries.FindAllUsersQueryUseCaseInputPort
import com.uramnoil.serverist.user.application.queries.FindUserByAccountIdQueryUseCaseInputPort
import com.uramnoil.serverist.user.application.queries.FindUserByNameQueryUseCaseInputPort
import com.uramnoil.serverist.user.infrastructure.application.command.CreateUserCommandUseCaseInteractor
import com.uramnoil.serverist.user.infrastructure.application.command.DeleteUserCommandUseCaseInteractor
import com.uramnoil.serverist.user.infrastructure.application.command.UpdateUserCommandUseCaseInteractor
import com.uramnoil.serverist.user.infrastructure.application.queries.ExposedFindAllUsersQueryUseCaseInteractor
import com.uramnoil.serverist.user.infrastructure.application.queries.ExposedFindUserByAccountIdQueryUseCaseInteractor
import com.uramnoil.serverist.user.infrastructure.application.queries.ExposedFindUserByNameQueryUseCaseInteractor
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