package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.uramnoil.serverist.application.user.commands.DeleteUserCommand
import com.uramnoil.serverist.application.user.commands.UpdateUserProfileCommand
import com.uramnoil.serverist.application.user.queries.FindUserByIdQuery
import org.kodein.di.DI
import org.kodein.di.instance
import java.util.*

fun SchemaBuilder.userSchema(di: DI) {
    query("findUserById") {
        resolver { id: UUID ->
            val query by di.instance<FindUserByIdQuery>()
            query.execute(id)
        }
    }

    mutation("updateUserProfile") {
        resolver { name: String, accountId: String, description: String, context: Context ->
            val id = context.getIdFromSession()

            val command by di.instance<UpdateUserProfileCommand>()
            command.execute(context.getIdFromSession(), accountId, name, description)
            id
        }

        accessRule(::checkSession)
    }

    mutation("deleteUser") {
        resolver { context: Context ->
            val id = context.getIdFromSession()

            val command by di.instance<DeleteUserCommand>()
            command.execute(context.getIdFromSession())
            id
        }

        accessRule(::checkSession)
    }
}