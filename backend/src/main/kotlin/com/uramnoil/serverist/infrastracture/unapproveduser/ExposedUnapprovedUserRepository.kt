package com.uramnoil.serverist.infrastracture.unapproveduser

import com.uramnoil.serverist.domain.models.unapproveduser.AccountId
import com.uramnoil.serverist.domain.models.unapproveduser.User
import com.uramnoil.serverist.domain.repositories.UnapprovedUserRepository
import com.uramnoil.serverist.domain.services.unapproveduser.UnapprovedUserFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import kotlin.coroutines.CoroutineContext

class ExposedUnapprovedUserRepository(private val database: Database, context: CoroutineContext) :
    UnapprovedUserRepository,
    CoroutineScope by CoroutineScope(context) {

    override fun storeAsync(user: User): Deferred<Unit> = async {
        newSuspendedTransaction(db = database) {
            UnapprovedUsers.update({ UnapprovedUsers.id eq user.accountId.value }) {
                it[email] = user.email.value
            }
            commit()
        }
    }

    override fun deleteAsync(user: User): Deferred<Unit> = async {
        newSuspendedTransaction(db = database) {
            UnapprovedUsers.deleteWhere { UnapprovedUsers.id eq user.accountId.value }
            commit()
        }
    }

    override fun findByAccountIdAsync(id: AccountId): Deferred<User?> = async {
        newSuspendedTransaction(db = database) {
            val result = UnapprovedUsers.select { UnapprovedUsers.id eq id.value }.firstOrNull()
                ?: return@newSuspendedTransaction null

            result.let {
                UnapprovedUserFactory.create(
                    result[UnapprovedUsers.id].value,
                    result[UnapprovedUsers.email],
                    result[UnapprovedUsers.hashedPassword]
                )
            }
        }
    }
}