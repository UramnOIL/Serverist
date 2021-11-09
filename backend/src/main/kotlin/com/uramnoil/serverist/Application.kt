package com.uramnoil.serverist

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.koin.application.buildAuthController
import com.uramnoil.serverist.koin.application.buildServeristControllers
import com.uramnoil.serverist.serverist.infrastructure.Servers
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.serialization.*
import io.ktor.sessions.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.slf4j.event.Level
import routing.routingAuth
import routing.routingGraphQL
import java.io.File
import com.uramnoil.serverist.auth.infrastructure.authenticated.Users as AuthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.Users as UnauthenticatedUsers
import com.uramnoil.serverist.serverist.infrastructure.Users as ServeristUsers

@Serializable
data class AuthSession(val id: Uuid) : Principal

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

/**
 * 本番環境用
 */
@Suppress("unused")
fun Application.mainModule(testing: Boolean = false) {
    install(StatusPages) {
        // 不正なリクエストパラメータ
        exception<ContentTransformationException> {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    // ContentNegotiation application/jsonをリクエストで使えるようにする
    install(ContentNegotiation) {
        json()
    }

    // Sessions サーバセッション `.sessions`にセッション情報を保存
    install(Sessions) {
        cookie<AuthSession>("SESSION", directorySessionStorage(File(".sessions"), cached = true)) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 1000
        }
    }

    // CallLogging リクエストのロギング用
    install(CallLogging) {
        level = Level.DEBUG
        format {
            val userId = it.sessions.get<AuthSession>()?.id ?: "Guest"
            val ip = it.request.local.remoteHost
            val status = it.response.status()
            val httpMethod = it.request.httpMethod.value
            val userAgent = it.request.headers["User-Agent"]
            val uri = it.request.uri
            "IP: $ip, User ID: $userId, User agent: $userAgent, Status: $status, HTTP method: $httpMethod, URI: $uri"
        }
    }
    productKoin()

    // ルーティング
    routingAuth()
    routingGraphQL()
}

fun Application.productKoin() {
    install(Koin) {
        val (userController, serverController) = buildServeristControllers()
        val authController = buildAuthController(userController)
        modules(module {
            single { userController }
            single { serverController }
            single { authController }
        })
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
            SchemaUtils.create(ServeristUsers, Servers, AuthenticatedUsers, UnauthenticatedUsers)
        }
    }
}