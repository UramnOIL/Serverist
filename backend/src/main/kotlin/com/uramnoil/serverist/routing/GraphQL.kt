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
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import org.koin.ktor.ext.inject

data class Server(
    val id: String,
    val createdAt: Long,
    val ownerId: String,
    val name: String,
    val host: String?,
    val port: Int?,
    val description: String
)

data class User(
    val id: String,
    val accountId: String,
    val name: String,
    val description: String
)

/**
 * GraphQL用のビルダ
 */
fun Application.routingGraphQL() = install(GraphQL) {
    playground = true
    endpoint = "/graphql"

    context { call ->
        // AuthSession所有時にコンテキストへ追加
        call.sessions.get<AuthSession>()?.let {
            +it
        }
    }

    schema {
        type<PageRequest>()
        type<Server>()
        type<User>()
        enum<Sort>()
        enum<OrderBy>()

        val serverController: ServerController by inject()

        serverSchema(serverController)

        val userController: UserController by inject()

        userSchema(userController)
    }
}
