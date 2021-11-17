package com.uramnoil.serverist.presenter

import com.uramnoil.serverist.application.authenticated.queries.FindUserByEmailAndPasswordQueryUseCaseInputPort
import com.uramnoil.serverist.application.authenticated.queries.FindUserByEmailAndPasswordQueryUseCaseOutputPort
import com.uramnoil.serverist.application.unauthenticated.queries.*
import com.uramnoil.serverist.application.unauthenticated.service.SendEmailToAuthenticateUseCaseInputPort
import com.uramnoil.serverist.application.unauthenticated.service.SendEmailToAuthenticateUseCaseOutputPort
import com.uramnoil.serverist.domain.common.exception.NotFoundException
import kotlinx.coroutines.currentCoroutineContext
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.time.ExperimentalTime
import com.uramnoil.serverist.application.authenticated.commands.CreateUserCommandUseCaseInputPort as CreateAuthenticatedUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.authenticated.commands.CreateUserCommandUseCaseOutputPort as CreateAuthenticatedUserCommandUseCaseOutputPort
import com.uramnoil.serverist.application.authenticated.commands.DeleteUserCommandUseCaseInputPort as DeleteAuthenticatedUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.authenticated.commands.DeleteUserCommandUseCaseOutputPort as DeleteAuthenticatedUserCommandUseCaseOutputPort
import com.uramnoil.serverist.application.unauthenticated.commands.CreateUserCommandUseCaseInputPort as CreateUnauthenticatedUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.unauthenticated.commands.CreateUserCommandUseCaseOutputPort as CreateUnauthenticatedUserCommandUseCaseOutputPort
import com.uramnoil.serverist.application.unauthenticated.commands.DeleteUserCommandUseCaseInputPort as DeleteUnauthenticatedUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.unauthenticated.commands.DeleteUserCommandUseCaseOutputPort as DeleteUnauthenticatedUserCommandUseCaseOutputPort


class AuthController(
    private val createUnauthenticatedUserCommandUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: CreateUnauthenticatedUserCommandUseCaseOutputPort) -> CreateUnauthenticatedUserCommandUseCaseInputPort,
    private val deleteUnauthenticatedUserCommandUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: DeleteUnauthenticatedUserCommandUseCaseOutputPort) -> DeleteUnauthenticatedUserCommandUseCaseInputPort,
    private val sendEmailToAuthenticateUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: SendEmailToAuthenticateUseCaseOutputPort) -> SendEmailToAuthenticateUseCaseInputPort,
    private val findUserByEmailAndPasswordQueryUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: FindUserByEmailAndPasswordQueryUseCaseOutputPort) -> FindUserByEmailAndPasswordQueryUseCaseInputPort,
    private val findUserByActivationCodeQueryUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: FindUserByActivationCodeQueryUseCaseOutputPort) -> FindUserByActivationCodeQueryUseCaseInputPort,
    private val findUserByEmailQueryUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: FindUserByEmailQueryUseCaseOutputPort) -> FindUserByEmailQueryUseCaseInputPort,
    private val createAuthenticatedCommandUserCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: CreateAuthenticatedUserCommandUseCaseOutputPort) -> CreateAuthenticatedUserCommandUseCaseInputPort,
    private val deleteAuthenticatedUserCommandUseCaseInputPortFactory: (coroutineContext: CoroutineContext, outputPort: DeleteAuthenticatedUserCommandUseCaseOutputPort) -> DeleteAuthenticatedUserCommandUseCaseInputPort,
    private val userController: UserController,     //HACK 別コンテキストの直接的な参照
) {
    @OptIn(ExperimentalTime::class)
    /**
     * サインアップ
     * メール認証あり
     */
    suspend fun signUp(email: String, password: String): Result<Unit> {
        val coroutineContext = currentCoroutineContext()

        val activationCode = UUID.randomUUID()
        val createResult = suspendCoroutine<Result<UUID>> {
            val outputPort = CreateUnauthenticatedUserCommandUseCaseOutputPort { result ->
                it.resume(result)
            }
            createUnauthenticatedUserCommandUseCaseInputPortFactory(coroutineContext, outputPort).execute(
                email,
                password,
                activationCode,
            )
        }

        createResult.onFailure {
            return Result.failure(it)
        }

        // メール送信
        return suspendCoroutine {
            val outputPort = SendEmailToAuthenticateUseCaseOutputPort { result ->
                it.resume(result)
            }
            sendEmailToAuthenticateUseCaseInputPortFactory(coroutineContext, outputPort).execute(email, activationCode)
        }
    }

    /**
     * ログイン
     * @return Result<UUID> ログイン情報が正しい時に、ユーザーIDを返す
     */
    suspend fun login(email: String, password: String): Result<UUID> {
        val coroutineContext = currentCoroutineContext()

        return suspendCoroutine<Result<UUID?>> {
            val outputPort = FindUserByEmailAndPasswordQueryUseCaseOutputPort { result ->
                it.resume(result)
            }
            findUserByEmailAndPasswordQueryUseCaseInputPortFactory(coroutineContext, outputPort).execute(
                email,
                password
            )
        }.mapCatching { it ?: throw NotFoundException("Invalid credential.") }
    }

    /**
     * アクティベーション
     */
    suspend fun activate(activationCode: UUID): Result<Unit> {
        val coroutineContext = currentCoroutineContext()

        val findResult = suspendCoroutine<Result<User?>> {
            val outputPort = FindUserByActivationCodeQueryUseCaseOutputPort { result ->
                it.resume(result)
            }
            findUserByActivationCodeQueryUseCaseInputPortFactory(coroutineContext, outputPort).execute(activationCode)
        }
        val user = findResult.getOrElse {
            return Result.failure(it)
        }

        // 使われていないactivationCodeの時
        user ?: return Result.failure(IllegalArgumentException("Illegal activation code"))

        val createAuthenticatedUserResult = suspendCoroutine<Result<UUID>> {
            val outputPort = CreateAuthenticatedUserCommandUseCaseOutputPort { result ->
                it.resume(result)
            }
            createAuthenticatedCommandUserCaseInputPortFactory(coroutineContext, outputPort).execute(
                user.email,
                user.hashedPassword
            )
        }

        val uuid = createAuthenticatedUserResult.getOrElse {
            return Result.failure(it)
        }

        // [a-zA-Z0-9]
        val characters = ('a'..'z') + ('A'..'Z') + ('0'..'9') + '_'
        // ランダムでアカウントIDを15文字で生成
        // (26 + 26 + 10) ^ 15なので衝突しないとみなす。衝突した際にもう一度トライする方が良い。
        val accountId = (1..15).map { characters.random() }.joinToString("")

        val createServeristUserResult =
            userController.create(uuid, accountId, listOf("Hoge", "Fuga").random(), "")

        // ServeristUserの作成失敗時
        createServeristUserResult.getOrElse {
            return Result.failure(it)
        }

        // 全て成功時にUnauthenticatedUserを削除
        return suspendCoroutine<Result<Unit>> {
            val outputPort = DeleteUnauthenticatedUserCommandUseCaseOutputPort { result ->
                it.resume(result)
            }
            deleteUnauthenticatedUserCommandUseCaseInputPortFactory(coroutineContext, outputPort).execute(user.id)
        }
    }

    /**
     * メール再送信
     */
    suspend fun resendAuthEmail(email: String): Result<Unit> {
        val coroutineContext = currentCoroutineContext()
        val findResult = suspendCoroutine<Result<User?>> {
            val outputPort = FindUserByEmailQueryUseCaseOutputPort { result ->
                it.resume(result)
            }
            findUserByEmailQueryUseCaseInputPortFactory(coroutineContext, outputPort).execute(email)
        }

        val user = findResult.getOrElse {
            return Result.failure(it)
        }

        user ?: return Result.failure(IllegalArgumentException("No inactive user has this email."))


        // メール送信
        return suspendCoroutine {
            val outputPort = SendEmailToAuthenticateUseCaseOutputPort { result ->
                it.resume(result)
            }
            sendEmailToAuthenticateUseCaseInputPortFactory(coroutineContext, outputPort).execute(
                user.email,
                user.activationCode
            )
        }
    }

    /**
     * 退会
     */
    suspend fun withdraw(id: UUID): Result<Unit> {
        val coroutineContext = currentCoroutineContext()
        // 認証情報の削除
        val deleteAuthenticatedUserResult = suspendCoroutine<Result<Unit>> {
            val outputPort = DeleteAuthenticatedUserCommandUseCaseOutputPort { result ->
                it.resume(result)
            }
            deleteAuthenticatedUserCommandUseCaseInputPortFactory(coroutineContext, outputPort).execute(id)
        }
        deleteAuthenticatedUserResult.getOrElse {
            // TODO: 失敗時も必ずユーザーのプロフィールも削除したい
            return Result.failure(it)
        }

        val deleteServeristUserResult = userController.delete(id)

        return deleteServeristUserResult
    }
}