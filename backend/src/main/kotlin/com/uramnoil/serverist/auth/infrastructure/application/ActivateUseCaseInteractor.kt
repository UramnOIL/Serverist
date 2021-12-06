package com.uramnoil.serverist.auth.infrastructure.application

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.auth.ActivateUseCaseInputPort
import com.uramnoil.serverist.application.auth.ActivateUseCaseOutputPort
import com.uramnoil.serverist.auth.infrastructure.UnauthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.toApplicationUnauthenticatedUser
import com.uramnoil.serverist.domain.auth.authenticated.models.User
import com.uramnoil.serverist.domain.auth.authenticated.repositories.UserRepository
import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.presenter.UserController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*
import kotlin.coroutines.CoroutineContext

class ActivateUseCaseInteractor(
    private val repository: UserRepository,
    private val userController: UserController,
    coroutineContext: CoroutineContext,
    private val outputPort: ActivateUseCaseOutputPort,
) : ActivateUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(code: Uuid) {
        launch {
            val result = runCatching {
                // 検索ステップ
                val row = newSuspendedTransaction {
                    UnauthenticatedUsers.select { UnauthenticatedUsers.activateCode eq code }.firstOrNull()
                }
                val user = row?.toApplicationUnauthenticatedUser()

                user ?: throw IllegalArgumentException("Illegal activation code")

                // AuthenticatedUser作成ステップ

                val id = UUID.randomUUID()
                val newResult = User.new(Id(id), Email(user.email), HashedPassword(user.hashedPassword))

                val newUser = newResult.getOrThrow()

                val insertResult = repository.insert(newUser)
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

                val findResult = repository.findById(Id(user.id))
                val domainUser = findResult.getOrThrow()

                domainUser ?: error("This is id of user that should not have already been deleted.") // 削除されているはずのないユーザー

                Unit
            }

            outputPort.handle(result)
        }
    }
}