package com.uramnoil.serverist.kodein.application

import com.uramnoil.serverist.application.user.commands.*
import com.uramnoil.serverist.application.user.queries.*
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.infrastructure.user.command.CreateUserCommandUseCaseInteractor
import com.uramnoil.serverist.infrastracture.user.queries.ExposedFindAllUsersQueryUseCaseInteractor
import com.uramnoil.serverist.infrastracture.user.queries.ExposedFindUserByAccountIdQueryUseCaseInteractor
import com.uramnoil.serverist.infrastracture.user.queries.ExposedFindUserByNameQueryUseCaseInteractor
import com.uramnoil.serverist.infrastructure.user.command.DeleteUserCommandUseCaseInteractor
import com.uramnoil.serverist.infrastructure.user.command.UpdateUserCommandUseCaseInteractor
import io.ktor.application.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import kotlin.coroutines.CoroutineContext

fun Application.buildUserApplicationDI(
    userRepository: UserRepository,
    context: CoroutineContext
) = DI {
    bind<CreateUserCommandUseCaseInputPort>() with factory { outputPort: CreateUserCommandUseCaseOutputPort ->
        CreateUserCommandUseCaseInteractor(outputPort, userRepository, context)
    }

    bind<DeleteUserCommandUseCaseInputPort>() with factory { outputPort: DeleteUserCommandUseCaseOutputPort ->
        DeleteUserCommandUseCaseInteractor(outputPort, userRepository, context)
    }

    bind<UpdateUserCommandUseCaseInputPort>() with factory { outputPort: UpdateUserCommandUseCaseOutputPort ->
        UpdateUserCommandUseCaseInteractor(outputPort, userRepository, context)
    }

    bind<FindAllUsersQueryUseCaseInputPort>() with factory { outputPort: FindAllUsersQueryUseCaseOutputPort ->
        ExposedFindAllUsersQueryUseCaseInteractor(outputPort, context)
    }

    bind<FindUserByAccountIdQueryUseCaseInputPort>() with factory { outputPort: FindUserByAccountIdQueryUseCaseOutputPort ->
        ExposedFindUserByAccountIdQueryUseCaseInteractor(outputPort, context)
    }

    bind<FindUserByNameQueryUseCaseInputPort>() with factory { outputPort: FindUserByNameQueryUseCaseOutputPort ->
        ExposedFindUserByNameQueryUseCaseInteractor(outputPort, context)
    }
}