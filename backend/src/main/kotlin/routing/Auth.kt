package routing

import com.uramnoil.serverist.AuthSession
import com.uramnoil.serverist.auth.application.unauthenticated.commands.AccountAlreadyExistsException
import com.uramnoil.serverist.auth.application.unauthenticated.commands.VerificationCodeHasAlreadyBeenSentException
import com.uramnoil.serverist.domain.common.exception.NotFoundException
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
                // サーバーエラー
                else -> {
                    call.respond(HttpStatusCode.InternalServerError)
                    log.error(it)
                }
            }
        }
    }

    // Email認証
    get("activate") {
        @Serializable
        data class AuthenticateUserId(val id: UUID)

        val (id) = call.receive<AuthenticateUserId>()

        val result = controller.activate(id)

        result.fold(
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
    }

    post("activate/resend") {
        call.respond(HttpStatusCode.InternalServerError)
    }

    authenticate {
        post("withdrawal") {
            val (id) = call.sessions.get<AuthSession>()!!
            controller.withdraw(id)
        }
    }
}