package com.uramnoil.serverist

import com.apurebase.kgraphql.GraphQL
import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.auth.application.authenticated.TryLoginUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.commands.AccountAlreadyExistsException
import com.uramnoil.serverist.auth.application.unauthenticated.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.commands.VerificationCodeHasAlreadyBeenSentException
import com.uramnoil.serverist.graphql.PageRequest
import com.uramnoil.serverist.graphql.serverSchema
import com.uramnoil.serverist.graphql.userSchema
import com.uramnoil.serverist.koin.domain.buildAuthDomainDI
import com.uramnoil.serverist.koin.domain.buildServerDomainDI
import com.uramnoil.serverist.koin.domain.buildUserDomainDI
import com.uramnoil.serverist.presenter.AuthController
import com.uramnoil.serverist.presenter.ServerController
import com.uramnoil.serverist.presenter.UserController
import com.uramnoil.serverist.server.application.commands.CreateServerCommandUseCaseInputPort
import com.uramnoil.serverist.server.application.commands.DeleteServerCommandUseCaseInputPort
import com.uramnoil.serverist.server.application.commands.UpdateServerCommandUseCaseInputPort
import com.uramnoil.serverist.server.application.queries.FindAllServersQueryUseCaseInputPort
import com.uramnoil.serverist.server.application.queries.FindServerByIdQueryUseCaseInputPort
import com.uramnoil.serverist.server.application.queries.FindServersByOwnerQueryUseCaseInputPort
import com.uramnoil.serverist.server.application.queries.OrderBy
import com.uramnoil.serverist.server.application.services.ServerService
import com.uramnoil.serverist.server.infrastructure.Servers
import com.uramnoil.serverist.user.application.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.user.application.commands.UpdateUserCommandUseCaseInputPort
import com.uramnoil.serverist.user.application.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.user.infrastructure.Users
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import java.io.File
import java.util.*

data class AuthSession(val id: Uuid) : Principal

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.productModule() {
    createConnection()

    install(Sessions) {
        cookie<AuthSession>("SESSION", directorySessionStorage(File(".sessions"), cached = true)) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 1000
        }
    }

    install(Authentication) {
        session<AuthSession>()
    }
}

fun Application.koin() {
    install(Koin) {
        val serverDomainDI = buildServerDomainDI()
        val userDomainDI = buildUserDomainDI()
        val authDomainDI = buildAuthDomainDI()
    }
}

/**
 * コネクションプールの作成
 */
fun Application.createConnection() {
    environment.config.apply {
        val host = property("database.host").getString()
        val database = property("database.database").getString()
        val port = property("database.port").getString()

        Database.connect(
            url = "jdbc:mysql://${host}:${port}/${database}?characterEncoding=utf8&useSSL=false",
            driver = com.mysql.cj.jdbc.Driver::class.qualifiedName!!,
            user = property("database.user").getString(),
            password = property("database.password").getString()
        )

        transaction {
            SchemaUtils.create(Users, Servers)
        }
    }
}

/**
 * 認証関連のルーティング
 */
fun Application.routingAuth() = routing {
    val tryLoginUseCaseInputPort: TryLoginUseCaseInputPort by inject()
    val createUserCommandUseCaseInputPort: CreateUserCommandUseCaseInputPort by inject()
    val controller = AuthController(
        tryLoginUseCaseInputPort = tryLoginUseCaseInputPort,
        createUserCommandUseCaseInputPort = createUserCommandUseCaseInputPort
    )

    // ログイン
    post("login") {
        data class CredentialContainer(val email: String, val password: String)

        val (email, password) = call.receive<CredentialContainer>()

        // すでにログイン済みだった場合
        if (call.sessions.get<AuthSession>() != null) {
            call.respond(HttpStatusCode.OK)
            return@post
        }

        // ログイン処理
        val loginResult = controller.login(email, password)

        val id = loginResult.getOrElse {
            // サーバーエラー
            call.respond(HttpStatusCode.InternalServerError)
            return@post
        }

        id ?: run {
            // 不正なクレデンシャル
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        call.sessions.set(AuthSession(id))
        call.respond(HttpStatusCode.OK)
    }

    // 登録
    post("signup") {
        data class EmailAndPassword(val email: String, val password: String)

        val (email, password) = call.receive<EmailAndPassword>()

        // サインアップ処理
        val createUserResult = controller.signUp(email, password)

        // 成功時
        createUserResult.onSuccess {
            call.respond(HttpStatusCode.OK)
        }

        // 失敗時
        createUserResult.onFailure {
            when (it) {
                // コードをすでに送信済み
                is VerificationCodeHasAlreadyBeenSentException -> {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "The email owner must have already been received a code to verify."
                    )
                }
                // Emailがすでに使われている
                is AccountAlreadyExistsException -> {
                    call.respond(HttpStatusCode.BadRequest, "This email has already been used.")
                }
                // サーバーエラー
                else -> {
                    call.respond(HttpStatusCode.InternalServerError)
                    log.error(it)
                }
            }
        }
    }

    // Email認証
    post("verify") {
        data class AuthenticateUserId(val id: UUID)

        val auth = call.receive<AuthenticateUserId>()
    }
}

/**
 * GraphQL用のビルダ
 */
fun Application.buildGraphql() = install(GraphQL) {
    playground = true

    wrap {
        authenticate(optional = true, build = it)
    }

    context { call ->
        // AuthSession所有時にコンテキストへ追加
        call.authentication.principal<AuthSession>()?.let {
            +it
        }
    }

    schema {
        stringScalar<Uuid> {
            deserialize = { Uuid.fromString(it) }
            serialize = Uuid::toString
        }

        type<PageRequest>()
        enum<Sort>()
        enum<OrderBy>()

        val service: ServerService by inject()
        val createCommand: CreateServerCommandUseCaseInputPort by inject()
        val updateCommand: UpdateServerCommandUseCaseInputPort by inject()
        val deleteCommand: DeleteServerCommandUseCaseInputPort by inject()
        val findByOwnerQuery: FindServersByOwnerQueryUseCaseInputPort by inject()
        val findAllQuery: FindAllServersQueryUseCaseInputPort by inject()
        val findByIdQuery: FindServerByIdQueryUseCaseInputPort by inject()

        serverSchema(
            ServerController(
                service = service,
                createCommand = createCommand,
                updateCommand = updateCommand,
                deleteCommand = deleteCommand,
                findByOwnerQuery = findByOwnerQuery,
                findAllQuery = findAllQuery,
                findByIdQuery = findByIdQuery
            )
        )

        val findUserByIdQueryUseCaseInputPort: FindUserByIdQueryUseCaseInputPort by inject()
        val updateUserCommandUseCaseInputPort: UpdateUserCommandUseCaseInputPort by inject()
        val deleteUserCommandUseCaseInputPort: DeleteUserCommandUseCaseInputPort by inject()

        userSchema(
            UserController(
                findUserByIdQueryUseCaseInputPort = findUserByIdQueryUseCaseInputPort,
                updateUserCommandUseCaseInputPort = updateUserCommandUseCaseInputPort,
                deleteUserCommandUseCaseInputPort = deleteUserCommandUseCaseInputPort
            )
        )
    }
}
