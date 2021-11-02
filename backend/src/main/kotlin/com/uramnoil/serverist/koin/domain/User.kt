package com.uramnoil.serverist.koin.domain

import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.infrastracture.user.repositories.ExposedUserRepository
import io.ktor.application.*
import org.koin.dsl.module

fun Application.buildUserDomainDI() = module {
    single<UserRepository> {
        ExposedUserRepository()
    }
}