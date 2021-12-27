package com.uramnoil.serverist.routing

import com.apurebase.kgraphql.GraphQL
import com.uramnoil.serverist.AuthSession
import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.graphql.PageRequest
import com.uramnoil.serverist.graphql.serverSchema
import com.uramnoil.serverist.graphql.userSchema
import com.uramnoil.serverist.presenter.ServerController
import com.uramnoil.serverist.presenter.UserController
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.authenticate
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import org.koin.ktor.ext.inject

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
        type<PageRequest>()
        enum<Sort>()
        enum<OrderBy>()

        val serverController: ServerController by inject()

        serverSchema(serverController)

        val userController: UserController by inject()

        userSchema(userController)
    }
}
