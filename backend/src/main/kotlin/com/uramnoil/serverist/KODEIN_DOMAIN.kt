package com.uramnoil.serverist

import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.domain.server.services.CreateServerService
import com.uramnoil.serverist.domain.unauthenticateduser.services.CreateUnauthenticatedUserService
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.domain.user.services.CreateUserService
import com.uramnoil.serverist.infrastracture.HashPasswordServiceImpl
import com.uramnoil.serverist.infrastracture.server.CreateServerServiceImpl
import com.uramnoil.serverist.infrastracture.server.ExposedServerRepository
import com.uramnoil.serverist.infrastracture.unauthenticated.CreateUnauthenticatedUserServiceImpl
import com.uramnoil.serverist.infrastracture.user.CreateUserServiceImpl
import com.uramnoil.serverist.infrastracture.user.ExposedUserRepository
import io.ktor.application.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.instance

fun ApplicationEnvironment.buildDomainDi(di: DI) = DI {
    this.extend(di)
    // <-- User -->

    bind<UserRepository>() with factory {
        ExposedUserRepository()
    }

    bind<CreateUserService>() with factory {
        CreateUserServiceImpl(instance())
    }

    // <-- UnapprovedUser -->

    bind<CreateUnauthenticatedUserService>() with factory {
        CreateUnauthenticatedUserServiceImpl(instance())
    }

    // <-- Server -->

    bind<ServerRepository>() with factory {
        ExposedServerRepository()
    }

    bind<CreateServerService>() with factory {
        CreateServerServiceImpl(instance())
    }

    // <-- Password -->

    bind<com.uramnoil.serverist.domain.kernel.services.HashPasswordService>() with factory {
        HashPasswordServiceImpl()
    }
}