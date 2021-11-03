package com.uramnoil.serverist.koin.application

import com.uramnoil.serverist.auth.application.authenticated.TryLoginUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.commands.DeleteUserCommandInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.service.SendEmailToAuthenticateService
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.commands.DeleteUserCommandInteractor
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.queries.FindUserByIdQueryUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.services.SpringBootSendEmailToAuthenticateService
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import com.uramnoil.serverist.user.infrastructure.application.queries.ExposedTryLoginInteractor
import io.ktor.application.*
import org.koin.dsl.module

fun Application.buildAuthApplicationDI(
    repository: UserRepository,
) = module {
    single<CreateUserCommandUseCaseInputPort> {
        //CreateUnauthenticatedUserCommandUseCaseInteractor(outputPort, get(), get())
        TODO("Implement CreateUserCommandUseCaseInteractor")
    }

    single<DeleteUserCommandInputPort> {
        DeleteUserCommandInteractor(repository)
    }

    single<FindUserByIdQueryUseCaseInputPort> {
        FindUserByIdQueryUseCaseInteractor(repository)
    }

    single<SendEmailToAuthenticateService> {
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


    single<TryLoginUseCaseInputPort> {
        ExposedTryLoginInteractor(get())
    }
}