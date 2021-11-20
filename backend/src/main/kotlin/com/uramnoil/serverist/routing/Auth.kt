package com.uramnoil.serverist.routing

import com.uramnoil.serverist.AuthSession
import com.uramnoil.serverist.application.unauthenticated.commands.AccountAlreadyExistsException
import com.uramnoil.serverist.application.unauthenticated.commands.VerificationCodeHasAlreadyBeenSentException
import com.uramnoil.serverist.domain.common.exception.NotFoundException
import com.uramnoil.serverist.domain.common.exception.UserNotFoundByIdException
import com.uramnoil.serverist.presenter.AuthController
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import java.util.*

/**
 * 認証関連のルーティング
 */
fun Application.routingAuth() = routing {
    val controller: AuthController by inject()
    post("login") {
        @Serializable
        data class CredentialParameters(val email: String, val password: String)

        val (email, password) = call.receive<CredentialParameters>()

        // すでにログイン済みだった場合
        if (call.sessions.get<AuthSession>() != null) {
            call.respond(HttpStatusCode.OK)
            return@post
        }

        // ログイン処理
        val loginResult = controller.login(email, password)
        val id = loginResult.getOrThrow()

        call.sessions.set(AuthSession(id))
        call.respond(HttpStatusCode.OK, Json.encodeToString(mapOf("id" to id.toString())))
    }

    authenticate("auth-session") {
        post("logout") {
            call.sessions.clear("AUTH")
            call.respond(HttpStatusCode.OK)
        }
    }

    // 登録
    post("signup") {
        @Serializable
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

                is IllegalArgumentException -> {
                    call.respond(HttpStatusCode.BadRequest, it.message ?: "Invalid parameters")
                }

                // サーバーエラー
                else -> {
                    log.error(it)
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }
    }

    // Email認証
    get("/activate") {
        log.info("hoge")
        val rowCode = call.request.queryParameters["code"] ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "`code` parameter is missing.")
            return@get
        }

        val codeResult = kotlin.runCatching { UUID.fromString(rowCode) }

        val code = codeResult.getOrElse {
            call.respond(HttpStatusCode.BadRequest, "Invalid code.")
            return@get
        }

        val result = controller.activate(code)

        val status = result.fold(
            {
                HttpStatusCode.OK
            },
            {
                when (it) {
                    is NotFoundException -> {
                        log.debug(it.message)
                        HttpStatusCode.BadRequest
                    }
                    else -> {
                        log.error(it)
                        HttpStatusCode.InternalServerError
                    }
                }
            }
        )

        call.respond(status)
    }

    post("activate/resend") {
        call.respond(HttpStatusCode.NoContent)
    }

    authenticate("auth-session") {
        post("withdrawal") {
            val session = call.sessions.get<AuthSession>() ?: run {
                log.info("hoge")
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val result = controller.withdraw(session.id)
            val status = result.fold(
                {
                    HttpStatusCode.OK
                },
                {
                    when (it) {
                        is UserNotFoundByIdException -> {
                            HttpStatusCode.BadRequest
                        }
                        else -> {
                            log.error(it)
                            HttpStatusCode.InternalServerError
                        }
                    }
                }
            )

            call.respond(status)
        }
    }
}