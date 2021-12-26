package com.uramnoil.serverist.routing

import com.uramnoil.serverist.AuthSession
import com.uramnoil.serverist.auth.application.AccountAlreadyExistsException
import com.uramnoil.serverist.domain.common.exception.NotFoundException
import com.uramnoil.serverist.domain.common.exception.UserNotFoundByIdException
import com.uramnoil.serverist.presenter.AuthController
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.sessions.clear
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 認証関連のルーティング
 */
fun Application.routingAuth() = routing {
    val controller: AuthController by inject()
    post("signin") {
        val coroutineContext = currentCoroutineContext()

        suspendCoroutine<Unit> {
            controller.login(call, coroutineContext) { result ->
                CoroutineScope(coroutineContext).launch {
                    val id = result.getOrElse {
                        when (it) {
                            is IllegalArgumentException -> {
                                call.respond(HttpStatusCode.BadRequest, it.message ?: "")
                            }
                            else -> {
                                call.respond(HttpStatusCode.InternalServerError)
                            }
                        }
                        return@launch
                    }

                    call.sessions.set(AuthSession(id))
                    call.respond(HttpStatusCode.OK, id.toString())
                }.invokeOnCompletion { _ ->
                    it.resume(Unit)
                }
            }
        }
    }

    authenticate("auth-session") {
        post("signout") {
            call.sessions.clear<AuthSession>()
            call.respond(HttpStatusCode.OK)
        }
    }

    // 登録
    post("signup") {
        val coroutineContext = currentCoroutineContext()

        // サインアップ処理
        suspendCoroutine<Unit> { continuation ->
            controller.signUp(call, coroutineContext) { result ->
                CoroutineScope(coroutineContext).launch {
                    // 成功時
                    result.onSuccess {
                        call.respond(HttpStatusCode.OK)
                    }
                    result.onFailure {
                        when (it) {
                            // Emailがすでに使われている
                            is AccountAlreadyExistsException -> {
                                call.respond(HttpStatusCode.BadRequest, "This email has already been used.")
                            }

                            is IllegalArgumentException -> {
                                call.respond(HttpStatusCode.BadRequest, it.message ?: "Invalid parameters")
                            }

                            // サーバーエラー
                            else -> {
                                log.error("Internal Error", it)
                                call.respond(HttpStatusCode.InternalServerError)
                            }
                        }
                    }
                }.invokeOnCompletion { _ ->
                    continuation.resume(Unit)
                }
            }
        }
    }

    // Email認証
    post("/activate") {
        val coroutineContext = currentCoroutineContext()

        suspendCoroutine<Unit> { continuation ->
            controller.activate(call, coroutineContext) {
                CoroutineScope(coroutineContext).launch {
                    val status = it.fold(
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
                                    log.error("Internal Error", it)
                                    HttpStatusCode.InternalServerError
                                }
                            }
                        }
                    )

                    call.respond(status)
                }.invokeOnCompletion { _ ->
                    continuation.resume(Unit)
                }
            }
        }
    }

    post("activate/resend") {
        call.respond(HttpStatusCode.NoContent)
    }

    authenticate("auth-session") {
        post("withdrawal") {
            val coroutineContext = currentCoroutineContext()

            suspendCoroutine<Unit> { continuation ->
                controller.withdraw(call, coroutineContext) {
                    CoroutineScope(coroutineContext).launch {
                        val status = it.fold(
                            {
                                HttpStatusCode.OK
                            },
                            {
                                when (it) {
                                    is UserNotFoundByIdException -> {
                                        HttpStatusCode.BadRequest
                                    }
                                    else -> {
                                        log.error("Internal Error", it)
                                        HttpStatusCode.InternalServerError
                                    }
                                }
                            }
                        )
                        call.respond(status)
                    }.invokeOnCompletion {
                        continuation.resume(Unit)
                    }
                }
            }
        }
    }
}