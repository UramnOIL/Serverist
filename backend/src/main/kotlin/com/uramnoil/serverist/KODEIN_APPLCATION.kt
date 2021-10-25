package com.uramnoil.serverist

import com.uramnoil.serverist.application.server.commands.CreateServerCommandInputPort
import com.uramnoil.serverist.application.server.commands.DeleteServerCommandInputPort
import com.uramnoil.serverist.application.server.commands.UpdateServerCommandInputPort
import com.uramnoil.serverist.application.server.queries.FindAllServersQueryInputPort
import com.uramnoil.serverist.application.server.queries.FindServerByIdQueryInputPort
import com.uramnoil.serverist.application.server.queries.FindServersByOwnerQueryInputPort
import com.uramnoil.serverist.application.server.queries.IsUserOwnerOfServer
import com.uramnoil.serverist.application.unauthenticateduser.commands.CreateUnauthenticatedUserCommandInputPort
import com.uramnoil.serverist.application.unauthenticateduser.commands.DeleteUnauthenticatedUserCommandInputPort
import com.uramnoil.serverist.application.unauthenticateduser.queries.FindUnauthenticatedUserByIdQueryInputPort
import com.uramnoil.serverist.application.unauthenticateduser.service.SendEmailToAuthenticateService
import com.uramnoil.serverist.application.user.commands.CreateUserCommandInputPort
import com.uramnoil.serverist.application.user.commands.DeleteUserCommandInputPort
import com.uramnoil.serverist.application.user.commands.UpdateUserProfileCommandInputPort
import com.uramnoil.serverist.application.user.queries.FindAllUsersQueryInputPort
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQueryInputPort
import com.uramnoil.serverist.application.user.queries.FindUserByNameQueryInputPort
import com.uramnoil.serverist.application.user.queries.GetUserIfCorrectLoginInfoQueryInputPort
import com.uramnoil.serverist.infrastracture.server.*
import com.uramnoil.serverist.infrastracture.unauthenticateduser.CreateUnauthenticatedUserCommandInteractor
import com.uramnoil.serverist.infrastracture.unauthenticateduser.DeleteUnauthenticatedUserCommandInteractor
import com.uramnoil.serverist.infrastracture.unauthenticateduser.FindUnauthenticatedUserByIdQueryInteractor
import com.uramnoil.serverist.infrastracture.unauthenticateduser.SpringBootSendEmailToAuthenticateService
import com.uramnoil.serverist.infrastracture.user.*
import io.ktor.application.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.instance

fun ApplicationEnvironment.buildApplicationDi(di: DI) = DI {
    this.extend(di)

    // <-- Server -->
    bind<CreateServerCommandInputPort>() with factory {
        CreateServerCommandInteractor(instance(), instance())
    }

    bind<DeleteServerCommandInputPort>() with factory {
        DeleteServerCommandInteractor(instance())
    }

    bind<UpdateServerCommandInputPort>() with factory {
        UpdateServerCommandInteractor(instance())
    }

    bind<FindServerByIdQueryInputPort>() with factory {
        FindServerByIdQueryInteractor()
    }

    bind<FindAllServersQueryInputPort>() with factory {
        ExposedFindAllServersQuery()
    }

    bind<FindServersByOwnerQueryInputPort>() with factory {
        ExposedFindServersByOwnerQuery()
    }

    bind<IsUserOwnerOfServer>() with factory {
        ExposedIsUserOwnerOfServerService()
    }

    // <-- User -->

    bind<CreateUserCommandInputPort>() with factory {
        CreateUserCommandInteractor(instance())
    }

    bind<DeleteUserCommandInputPort>() with factory {
        DeleteUserCommandInteractor(instance())
    }

    bind<UpdateUserProfileCommandInputPort>() with factory {
        UpdateUserProfileCommandInteractor(instance())
    }

    bind<FindAllUsersQueryInputPort>() with factory {
        ExposedFindAllUsersQuery()
    }

    bind<FindUserByAccountIdQueryInputPort>() with factory {
        ExposedFindUserByAccountIdQuery()
    }

    bind<FindUserByNameQueryInputPort>() with factory {
        ExposedFindUserByNameQuery()
    }

    bind<GetUserIfCorrectLoginInfoQueryInputPort>() with factory {
        ExposedGetUserIfCorrectLoginInfoQuery(instance())
    }

    // <-- UnauthenticatedUser -->
    bind<CreateUnauthenticatedUserCommandInputPort>() with factory {
        CreateUnauthenticatedUserCommandInteractor(instance(), instance())
    }

    bind<DeleteUnauthenticatedUserCommandInputPort>() with factory {
        DeleteUnauthenticatedUserCommandInteractor(instance())
    }

    bind<FindUnauthenticatedUserByIdQueryInputPort>() with factory {
        FindUnauthenticatedUserByIdQueryInteractor(instance())
    }

    bind<SendEmailToAuthenticateService>() with factory {
        config.run {
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
}
