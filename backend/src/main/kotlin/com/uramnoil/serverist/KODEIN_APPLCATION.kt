package com.uramnoil.serverist

import com.uramnoil.serverist.application.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.server.commands.DeleteServerCommand
import com.uramnoil.serverist.application.server.commands.UpdateServerCommand
import com.uramnoil.serverist.application.server.queries.FindAllServersQuery
import com.uramnoil.serverist.application.server.queries.FindServerByIdQuery
import com.uramnoil.serverist.application.server.queries.FindServersByOwnerQuery
import com.uramnoil.serverist.application.server.queries.IsUserOwnerOfServer
import com.uramnoil.serverist.application.unauthenticateduser.commands.CreateUnauthenticatedUserCommand
import com.uramnoil.serverist.application.unauthenticateduser.commands.DeleteUnauthenticatedUserCommand
import com.uramnoil.serverist.application.unauthenticateduser.service.SendEmailToAuthenticateService
import com.uramnoil.serverist.application.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.user.commands.DeleteUserCommand
import com.uramnoil.serverist.application.user.commands.UpdateUserProfileCommand
import com.uramnoil.serverist.application.user.queries.FindAllUsersQuery
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQuery
import com.uramnoil.serverist.application.user.queries.FindUserByNameQuery
import com.uramnoil.serverist.application.user.queries.ValidateLoginService
import com.uramnoil.serverist.infrastracture.server.*
import com.uramnoil.serverist.infrastracture.unauthenticateduser.CreateUnauthenticatedUserCommandImpl
import com.uramnoil.serverist.infrastracture.unauthenticateduser.DeleteUnauthenticatedUserCommandImpl
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
    bind<CreateServerCommand>() with factory {
        CreateServerCommandImpl(instance(), instance())
    }

    bind<DeleteServerCommand>() with factory {
        DeleteServerCommandImpl(instance())
    }

    bind<UpdateServerCommand>() with factory {
        UpdateServerCommandImpl(instance())
    }

    bind<FindServerByIdQuery>() with factory {
        FindServerByIdQueryImpl()
    }

    bind<FindAllServersQuery>() with factory {
        ExposedFindAllServersQuery()
    }

    bind<FindServersByOwnerQuery>() with factory {
        ExposedFindServersByOwnerQuery()
    }

    bind<IsUserOwnerOfServer>() with factory {
        ExposedIsUserOwnerOfServerService()
    }

    // <-- User -->

    bind<CreateUserCommand>() with factory {
        CreateUserCommandImpl(instance())
    }

    bind<DeleteUserCommand>() with factory {
        DeleteUserCommandImpl(instance())
    }

    bind<UpdateUserProfileCommand>() with factory {
        UpdateUserProfileCommandImpl(instance())
    }

    bind<FindAllUsersQuery>() with factory {
        ExposedFindAllUsersQuery()
    }

    bind<FindUserByAccountIdQuery>() with factory {
        ExposedFindUserByAccountIdQuery()
    }

    bind<FindUserByNameQuery>() with factory {
        ExposedFindUserByNameQuery()
    }

    bind<ValidateLoginService>() with factory {
        ExposedValidateLoginService(instance())
    }

    // <-- UnauthenticatedUser -->
    bind<CreateUnauthenticatedUserCommand>() with factory {
        CreateUnauthenticatedUserCommandImpl(instance(), instance())
    }

    bind<DeleteUnauthenticatedUserCommand>() with factory {
        DeleteUnauthenticatedUserCommandImpl(instance())
    }

    bind<SendEmailToAuthenticateService>() with factory {
        config.run {
            SpringBootSendEmailToAuthenticateService(
                property("mail.host").getString(),
                property("mail.port").getString().toInt(),
                property("mail.username").getString(),
                property("mail.password").getString(),
                property("mail.from").getString(),
                property("mail.url").getString()
            )
        }
    }
}
