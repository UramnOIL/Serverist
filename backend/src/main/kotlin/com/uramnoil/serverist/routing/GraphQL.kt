package com.uramnoil.serverist.routing

import com.apurebase.kgraphql.GraphQL
import com.uramnoil.serverist.AuthSession
import com.uramnoil.serverist.graphql.PageRequest
import com.uramnoil.serverist.graphql.serverSchema
import com.uramnoil.serverist.graphql.userSchema
import com.uramnoil.serverist.presenter.ServerController
import com.uramnoil.serverist.presenter.UserController
import com.uramnoil.serverist.serverist.application.OrderBy
import com.uramnoil.serverist.serverist.application.Sort
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.authenticate
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import kotlinx.datetime.Instant
import org.koin.ktor.ext.inject
import java.util.UUID


/**
 * GraphQL用のビルダ
 */
fun Application.routingGraphQL() = install(GraphQL) {
    playground = true

    wrap {
        authenticate("auth-session", optional = true, build = it)
    }

    context { call ->
        // AuthSession所有時にコンテキストへ追加
        call.sessions.get<AuthSession>()?.let {
            +it
        }
    }

    schema {
        stringScalar<UUID> {
            deserialize = UUID::fromString
            serialize = UUID::toString
        }

        longScalar<Instant> {
            deserialize = { Instant.fromEpochMilliseconds(it) }
            serialize = { it.toEpochMilliseconds() }
        }

        type<PageRequest>()
        enum<Sort>()
        enum<OrderBy>()

        val serverController: ServerController by inject()

        serverSchema(serverController)

        val userController: UserController by inject()

        userSchema(userController)
    }
}
