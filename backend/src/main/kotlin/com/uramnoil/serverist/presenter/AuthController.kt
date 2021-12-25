package com.uramnoil.serverist.presenter

import com.uramnoil.serverist.AuthSession
import com.uramnoil.serverist.auth.application.ActivateUseCaseInputPort
import com.uramnoil.serverist.auth.application.ActivateUseCaseOutputPort
import com.uramnoil.serverist.auth.application.SignInUseCaseOutputPort
import com.uramnoil.serverist.auth.application.SignUpUseCaseInputPort
import com.uramnoil.serverist.auth.application.SignUpUseCaseOutputPort
import com.uramnoil.serverist.auth.application.SingInUseCaseInputPort
import com.uramnoil.serverist.auth.application.WithdrawUseCaseInputPort
import com.uramnoil.serverist.auth.application.WithdrawUseCaseOutputPort
import io.ktor.application.ApplicationCall
import io.ktor.request.receive
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.slf4j.Logger
import java.util.UUID
import kotlin.coroutines.CoroutineContext
import kotlin.time.ExperimentalTime


class AuthController(
    private val log: Logger,
    private val signUpUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: SignUpUseCaseOutputPort) -> SignUpUseCaseInputPort,
    private val singInUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: SignInUseCaseOutputPort) -> SingInUseCaseInputPort,
    private val activateUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: ActivateUseCaseOutputPort) -> ActivateUseCaseInputPort,
    private val withdrawUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: WithdrawUseCaseOutputPort) -> WithdrawUseCaseInputPort,
) {
    @OptIn(ExperimentalTime::class)
            /**
             * サインアップ
             * メール認証あり
             */
    fun signUp(call: ApplicationCall, coroutineContext: CoroutineContext, outputPort: SignUpUseCaseOutputPort) {
        @Serializable
        data class EmailAndPassword(val email: String, val password: String)

        CoroutineScope(coroutineContext).launch {
            val (email, password) = call.receive<EmailAndPassword>()
            val activationCode = UUID.randomUUID()

            signUpUseCaseInputPortFactory(coroutineContext, outputPort).execute(email, password, activationCode)
        }
    }

    /**
     * ログイン
     * @return Result<UUID> ログイン情報が正しい時に、ユーザーIDを返す
     */
    fun login(
        call: ApplicationCall,
        coroutineContext: CoroutineContext,
        outputPort: SignInUseCaseOutputPort
    ) {
        @Serializable
        data class CredentialParameters(val email: String, val password: String)

        CoroutineScope(coroutineContext).launch {
            val auth = call.sessions.get<AuthSession>()
            // すでにログイン済みだった場合
            if (auth != null) {
                outputPort.handle(Result.success(auth.id))
                return@launch
            }

            val (email, password) = call.receive<CredentialParameters>()

            singInUseCaseInputPortFactory(currentCoroutineContext(), outputPort).execute(email, password)
        }
    }

    /**
     * アクティベーション
     */
    fun activate(
        call: ApplicationCall,
        coroutineContext: CoroutineContext,
        outputPort: ActivateUseCaseOutputPort
    ) {
        @Serializable
        data class EmailAndActivationCode(val email: String, val activationCode: Int)

        CoroutineScope(coroutineContext).launch {
            val (email, activationCode) = call.receive<EmailAndActivationCode>()
            activateUseCaseInputPortFactory(coroutineContext, outputPort).execute(email, activationCode)
        }
    }

    /**
     * メール再送信
     */
    fun resendAuthEmail(
        call: ApplicationCall,
        coroutineContext: CoroutineContext,
    ) {
//        val coroutineContext = currentCoroutineContext()
//        val findResult = suspendCoroutine<Result<User?>> {
//            val outputPort = FindUserByEmailQueryUseCaseOutputPort { result ->
//                it.resume(result)
//            }
//            findUserByEmailQueryUseCaseInputPortFactory(coroutineContext, outputPort).execute(email)
//        }
//
//        val user = findResult.getOrElse {
//            return Result.failure(it)
//        }
//
//        user ?: return Result.failure(IllegalArgumentException("No inactive user has this email."))
//
//
//        // メール送信
//        return suspendCoroutine {
//            val outputPort = SendEmailToAuthenticateUseCaseOutputPort { result ->
//                it.resume(result)
//            }
//            sendEmailToAuthenticateUseCaseInputPortFactory(coroutineContext, outputPort).execute(
//                user.email,
//                user.activationCode
//            )
//        }
    }

    /**
     * 退会
     */
    fun withdraw(
        call: ApplicationCall,
        coroutineContext: CoroutineContext,
        outputPort: WithdrawUseCaseOutputPort
    ) {
        val auth = call.sessions.get<AuthSession>()!!
        withdrawUseCaseInputPortFactory(coroutineContext, outputPort).execute(auth.id)
    }
}