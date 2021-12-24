package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.uramnoil.serverist.presenter.UserController
import java.util.UUID

fun SchemaBuilder.userSchema(controller: UserController) {
    query("findUserById") {
        resolver { id: UUID ->
            controller.findById(id).getOrThrow()
        }
    }

    mutation("updateUser") {
        resolver { name: String, accountId: String, description: String, context: Context ->
            val id = context.getIdFromSession()
            controller.update(id, accountId, name, description)
            controller.findById(id).getOrThrow()
        }

        accessRule(::requireAuthSession)
    }
}
