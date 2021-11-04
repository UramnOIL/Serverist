package com.uramnoil.serverist.presenter

import com.uramnoil.serverist.auth.application.authenticated.queries.FindUserByEmailAndPasswordQueryUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.queries.FindUserByActivationCodeQueryUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.queries.FindUserByEmailQueryUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.service.SendEmailToAuthenticateUseCase
import java.util.*
import kotlin.time.ExperimentalTime
import com.uramnoil.serverist.auth.application.authenticated.commands.CreateUserCommandUseCaseInputPort as CreateAuthenticatedUserCommandUseCaseInputPort
import com.uramnoil.serverist.auth.application.authenticated.commands.DeleteUserCommandUseCaseInputPort as DeleteAuthenticatedUserCommandUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.commands.CreateUserCommandUseCaseInputPort as CreateUnauthenticatedUserCommandUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.commands.DeleteUserCommandUseCaseInputPort as DeleteUnauthenticatedUserCommandUseCaseInputPort


class AuthController(
    private val createUserCommandUseCaseInputPort: CreateUnauthenticatedUserCommandUseCaseInputPort,
    private val deleteUnauthenticatedUserUseCaseInputPort: DeleteUnauthenticatedUserCommandUseCaseInputPort,
    private val sendEmailToAuthenticateUseCase: SendEmailToAuthenticateUseCase,
    private val findUserByEmailAndPasswordQueryUseCaseInputPort: FindUserByEmailAndPasswordQueryUseCaseInputPort,
    private val findUserByActivationCodeQueryUseCaseInputPort: FindUserByActivationCodeQueryUseCaseInputPort,
    private val findUserByEmailQueryUseCaseInputPort: FindUserByEmailQueryUseCaseInputPort,
    private val createAuthenticatedCommandUserCaseInputPort: CreateAuthenticatedUserCommandUseCaseInputPort,
    private val deleteAuthenticatedUserCommandUseCaseInputPort: DeleteAuthenticatedUserCommandUseCaseInputPort,
    private val userController: UserController,     //HACK 別コンテキストの直接的な参照
) {
    @OptIn(ExperimentalTime::class)
    /**
     * サインアップ
     * メール認証あり
     */
    suspend fun signUp(email: String, password: String): Result<Unit> {
        val authenticationCode = UUID.randomUUID()

        val createResult = createUserCommandUseCaseInputPort.execute(
            email,
            password,
            authenticationCode,
        )

        createResult.onFailure {
            return Result.failure(it)
        }

        // メール送信
        return sendEmailToAuthenticateUseCase.execute(email, authenticationCode)
    }

    /**
     * ログイン
     * @return Result<UUID?> ログイン情報が正しい時に、ユーザーIDを返す
     */
    suspend fun login(email: String, password: String): Result<UUID?> {
        return findUserByEmailAndPasswordQueryUseCaseInputPort.execute(email, password)
    }

    /**
     * アクティベーション
     */
    suspend fun activate(activationCode: UUID): Result<Unit> {
        val findResult = findUserByActivationCodeQueryUseCaseInputPort.execute(activationCode)

        val user = findResult.getOrElse {
            return Result.failure(it)
        }

        // 使われていないactivationCodeの時
        user ?: return Result.failure(IllegalArgumentException("Illegal activation code"))

        val createAuthenticatedUserResult =
            createAuthenticatedCommandUserCaseInputPort.execute(user.email, user.hashedPassword)

        createAuthenticatedUserResult.getOrElse {
            return Result.failure(it)
        }

        // [a-zA-Z0-9]
        val characters = ('a'..'z') + ('A'..'Z') + ('0'..'9') + '_'
        // ランダムでアカウントIDを15文字で生成
        // (26 + 26 + 10) ^ 15なので衝突しないとみなす。衝突した際にもう一度トライする方が良い。
        val accountId = (1..15).map { characters.random() }.joinToString("")

        val createServeristUserResult =
            userController.create(UUID.randomUUID(), accountId, listOf("Hoge", "Fuga").random(), "")

        // ServeristUserの作成失敗時
        createServeristUserResult.getOrElse {
            return Result.failure(it)
        }

        // 全て成功時にUnauthenticatedUserを削除
        return deleteUnauthenticatedUserUseCaseInputPort.execute(user.id)
    }

    /**
     * メール再送信
     */
    suspend fun resendAuthEmail(email: String): Result<Unit> {
        val findResult = findUserByEmailQueryUseCaseInputPort.execute(email)

        val user = findResult.getOrElse {
            return Result.failure(it)
        }

        user ?: return Result.failure(IllegalArgumentException("No inactive user has this email."))

        return sendEmailToAuthenticateUseCase.execute(user.email, user.activationCode)
    }

    /**
     * 退会
     */
    suspend fun withdraw(id: UUID): Result<Unit> {
        // 認証情報の削除
        val deleteAuthenticatedUserResult = deleteAuthenticatedUserCommandUseCaseInputPort.execute(id)
        deleteAuthenticatedUserResult.getOrElse {
            // TODO: 失敗時も必ずユーザーのプロフィールも削除したい
            return Result.failure(it)
        }

        val deleteServeristUserResult = userController.delete(id)

        return deleteServeristUserResult
    }
}