package com.uramnoil.serverist

import com.uramnoil.serverist.auth.infrastructure.AuthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.UnauthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.domain.repositories.ExposedUserRepositoryImpl
import com.uramnoil.serverist.exceptions.NoAuthorityException
import com.uramnoil.serverist.koin.application.buildAuthController
import com.uramnoil.serverist.koin.application.buildServeristControllers
import com.uramnoil.serverist.routing.routingAuth
import com.uramnoil.serverist.routing.routingGraphQL
import com.uramnoil.serverist.serverist.infrastructure.Servers
import com.uramnoil.serverist.serverist.infrastructure.domain.repositories.ExposedServerRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.serialization.*
import io.ktor.sessions.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.slf4j.event.Level
import java.io.File
import java.util.*
import com.uramnoil.serverist.auth.infrastructure.domain.repositories.ExposedUserRepository as ExposedUnauthenticatedUserRepository
import com.uramnoil.serverist.serverist.infrastructure.Users as ServeristUsers
import com.uramnoil.serverist.serverist.infrastructure.domain.repositories.ExposedUserRepository as ExposedServeristUserRepository

object UUIDSerializer : KSerializer<UUID> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}

@Serializable
data class AuthSession(@Serializable(with = UUIDSerializer::class) val id: UUID) : Principal

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

/**
 * 本番環境用
 */
@Suppress("unused")
fun Application.mainModule() {
    install(StatusPages) {
        // 不正なリクエストパラメータ
        exception<ContentTransformationException> {
            call.respond(HttpStatusCode.BadRequest)
        }
        // アクセス権限がない
        exception<NoAuthorityException> {
            call.respond(HttpStatusCode.Forbidden)
        }
    }

    // ContentNegotiation application/jsonをリクエストで使えるようにする
    install(ContentNegotiation) {
        json()
    }

    // Sessions サーバセッション `.sessions`にセッション情報を保存
    install(Sessions) {
        cookie<AuthSession>("AUTH", directorySessionStorage(File(".sessions"), cached = true)) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 1000
        }
    }

    // CallLogging リクエストのロギング用
    install(CallLogging) {
        level = Level.INFO
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
    installFormAuth()

    // ルーティング
    routingAuth()
    routingGraphQL()
}

fun Application.installFormAuth() {
    install(Authentication) {
        session<AuthSession>("auth-session") {
            validate { it }
            skipWhen { it.sessions.get<AuthSession>() != null }
        }
    }
}

fun Application.productKoin() {
    install(Koin) {
        val serveristUserRepository = ExposedServeristUserRepository()
        val authenticatedUserRepository = ExposedUserRepositoryImpl()
        val unauthenticatedUserRepository = ExposedUnauthenticatedUserRepository()
        val serverRepository = ExposedServerRepository()
        val (userController, serverController) = buildServeristControllers(serveristUserRepository, serverRepository)
        val authController =
            buildAuthController(unauthenticatedUserRepository, authenticatedUserRepository, userController)
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
fun Application.createMySqlConnection() {
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