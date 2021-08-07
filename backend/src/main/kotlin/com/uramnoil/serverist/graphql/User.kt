package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.commands.DeleteUserCommandInputPort
import com.uramnoil.serverist.application.user.commands.UpdateUserProfileCommandInputPort
import com.uramnoil.serverist.application.user.queries.FindUserByIdQueryInputPort
import org.kodein.di.DI
import org.kodein.di.instance

fun SchemaBuilder.userSchema(di: DI) {
    query("findUserById") {
        resolver { id: Uuid ->
            val query by di.instance<FindUserByIdQueryInputPort>()
            query.execute(id)
        }
    }

    mutation("updateUserProfile") {
        resolver { name: String, accountId: String, description: String, context: Context ->
            val id = context.getIdFromSession()

            val command by di.instance<UpdateUserProfileCommandInputPort>()
            command.execute(context.getIdFromSession(), accountId, name, description)
            id
        }

        accessRule(::checkSession)
    }

    mutation("deleteUser") {
        resolver { context: Context ->
            val id = context.getIdFromSession()

            val command by di.instance<DeleteUserCommandInputPort>()
            command.execute(context.getIdFromSession())
            id
        }

        accessRule(::checkSession)
    }
}