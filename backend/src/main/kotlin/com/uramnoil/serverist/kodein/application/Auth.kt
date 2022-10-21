package com.uramnoil.serverist.kodein.application

import com.uramnoil.serverist.application.auth.authenticated.*
import com.uramnoil.serverist.application.auth.unauthenticated.commands.*
import com.uramnoil.serverist.application.auth.unauthenticated.queries.*
import com.uramnoil.serverist.application.auth.unauthenticated.service.SendEmailToAuthenticateService
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import com.uramnoil.serverist.infrastracture.auth.unauthenticated.application.commands.DeleteUserCommandInteractor
import com.uramnoil.serverist.infrastracture.auth.unauthenticated.application.queries.FindUserByIdQueryUseCaseInteractor
import com.uramnoil.serverist.infrastructure.auth.unauthenticated.application.services.SpringBootSendEmailToAuthenticateService
import com.uramnoil.serverist.infrastructure.user.queries.ExposedTryLoginInteractor
import io.ktor.application.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.instance
import kotlin.coroutines.CoroutineContext

fun Application.buildAuthApplicationDI(
    repository: UserRepository,
    context: CoroutineContext
) = DI {
    bind<CreateUserCommandUseCaseInputPort>() with factory { outputPort: CreateUserCommandUseCaseOutputPort ->
        //CreateUnauthenticatedUserCommandUseCaseInteractor(outputPort, instance(), instance(), context)
        TODO("Implement CreateUserCommandUseCaseInteractor")
    }

    bind<DeleteUserCommandInputPort>() with factory { outputPort: DeleteUserCommandOutputPort ->
        DeleteUserCommandInteractor(outputPort, repository, context)
    }

    bind<FindUserByIdQueryUseCaseInputPort>() with factory { outputPort: FindUserByIdQueryUseCaseOutputPort ->
        FindUserByIdQueryUseCaseInteractor(outputPort, repository, context)
    }

    bind<SendEmailToAuthenticateService>() with factory {
        this@buildAuthApplicationDI.environment.config.run {
            SpringBootSendEmailToAuthenticateService(
                property("mail.host").getString(),
                property("mail.port").getString().toInt(),
                property("mail.username").getString(),
                property("mail.password").getString(),
                property("mail.from").getString(),
                property("mail.auth_url").getString()
            )
        }
    }


    bind<TryLoginUseCaseInputPort>() with factory { outputPort: TryLoginUseCaseOutputPort ->
        ExposedTryLoginInteractor(outputPort, instance(), context)
    }
}