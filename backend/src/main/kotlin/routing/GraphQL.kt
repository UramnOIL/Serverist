package routing

import com.apurebase.kgraphql.GraphQL
import com.uramnoil.serverist.AuthSession
import com.uramnoil.serverist.Sort
import com.uramnoil.serverist.graphql.PageRequest
import com.uramnoil.serverist.graphql.serverSchema
import com.uramnoil.serverist.graphql.userSchema
import com.uramnoil.serverist.presenter.ServerController
import com.uramnoil.serverist.presenter.UserController
import com.uramnoil.serverist.serverist.application.server.queries.OrderBy
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.sessions.*
import org.koin.ktor.ext.inject
import java.util.*


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

        type<PageRequest>()
        enum<Sort>()
        enum<OrderBy>()

        val serverController: ServerController by inject()

        serverSchema(serverController)

        val userController: UserController by inject()

        userSchema(userController)
    }
}
