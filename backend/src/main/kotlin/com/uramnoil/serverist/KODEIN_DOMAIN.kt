package com.uramnoil.serverist

import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.domain.server.services.CreateServerService
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.domain.user.services.CreateUserService
import com.uramnoil.serverist.infrastracture.HashPasswordServiceImpl
import com.uramnoil.serverist.infrastracture.server.CreateServerServiceImpl
import com.uramnoil.serverist.infrastracture.server.ExposedServerRepository
import com.uramnoil.serverist.infrastracture.user.CreateUserServiceImpl
import com.uramnoil.serverist.infrastracture.user.ExposedUserRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun buildDomainDi(di: DI) = DI {
    this.extend(di)
    // <-- User -->

    bind<UserRepository>() with singleton {
        ExposedUserRepository()
    }

    bind<CreateUserService>() with singleton {
        CreateUserServiceImpl(instance())
    }

    // <-- UnapprovedUser -->

    // <-- Server -->

    bind<ServerRepository>() with singleton {
        ExposedServerRepository()
    }

    bind<CreateServerService>() with singleton {
        CreateServerServiceImpl(instance())
    }

    // <-- Password -->

    bind<com.uramnoil.serverist.domain.kernel.services.HashPasswordService>() with singleton {
        HashPasswordServiceImpl()
    }
}