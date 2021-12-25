package com.uramnoil.serverist.auth.infrastructure.application

import com.uramnoil.serverist.auth.application.ActivateUseCaseInputPort
import com.uramnoil.serverist.auth.application.ActivateUseCaseOutputPort
import com.uramnoil.serverist.auth.infrastructure.UnauthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.toApplicationUnauthenticatedUser
import com.uramnoil.serverist.domain.auth.authenticated.models.User
import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.presenter.UserController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import kotlin.coroutines.CoroutineContext
import com.uramnoil.serverist.domain.auth.authenticated.repositories.UserRepository as AuthenticatedUserRepository
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository as UnauthenticatedUserRepository

class ActivateUseCaseInteractor(
    private val authenticatedUserRepository: AuthenticatedUserRepository,
    private val unauthenticatedUserRepository: UnauthenticatedUserRepository,
    private val userController: UserController,
    coroutineContext: CoroutineContext,
    private val outputPort: ActivateUseCaseOutputPort,
) : ActivateUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(email: String, activationCode: Int) {
        launch {
            val result = runCatching {
                // 検索ステップ
                val row = newSuspendedTransaction {
                    UnauthenticatedUsers.select { (UnauthenticatedUsers.email eq email) and (UnauthenticatedUsers.activateCode eq activationCode) }
                        .firstOrNull()
                }
                val user = row?.toApplicationUnauthenticatedUser()

                user ?: throw IllegalArgumentException("Illegal activation code")

                // AuthenticatedUser作成ステップ

                val id = UUID.randomUUID()
                val newResult = User.new(Id(id), Email(user.email), HashedPassword(user.hashedPassword))

                val newUser = newResult.getOrThrow()

                val insertResult = authenticatedUserRepository.insert(newUser)
                insertResult.getOrThrow()

                // ServeristUser作成ステップ

                // [a-zA-Z0-9]
                val characters = ('a'..'z') + ('A'..'Z') + ('0'..'9') + '_'
                // ランダムでアカウントIDを15文字で生成
                // (26 + 26 + 10) ^ 15なので衝突しないとみなす。衝突した際にもう一度トライする方が良い。
                val accountId = (1..15).map { characters.random() }.joinToString("")

                val createResult = userController.create(id, accountId, listOf("Hoge", "Fuga").random(), "")
                createResult.getOrThrow()

                // UnauthenticatedUser削除ステップ

                // UnauthenticatedUserを検索
                val findResult = unauthenticatedUserRepository.findById(
                    com.uramnoil.serverist.domain.auth.unauthenticated.models.Id(user.id)
                )
                val domainUser = findResult.getOrThrow()

                domainUser ?: error("This is id of user that should not have already been deleted.") // 削除されているはずのないユーザー

                val deleteResult = unauthenticatedUserRepository.delete(domainUser)
                deleteResult.getOrThrow()
            }

            outputPort.handle(result)
        }
    }
}