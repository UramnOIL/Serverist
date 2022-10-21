package com.uramnoil.serverist.kodein.domain

import com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService
import com.uramnoil.serverist.infrastructure.HashPasswordServiceImpl
import io.ktor.application.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory

fun Application.buildAuthDomainDI() = DI {
    bind<HashPasswordService>() with factory {
        HashPasswordServiceImpl()
    }
}