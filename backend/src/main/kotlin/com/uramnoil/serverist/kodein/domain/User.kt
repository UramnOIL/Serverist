package com.uramnoil.serverist.kodein.domain

import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.infrastracture.user.repositories.ExposedUserRepository
import io.ktor.application.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory

fun Application.buildUserDomainDI() = DI {
    bind<UserRepository>() with factory {
        ExposedUserRepository()
    }
}