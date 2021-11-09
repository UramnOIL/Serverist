package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.uramnoil.serverist.presenter.UserController
import java.util.*

fun SchemaBuilder.userSchema(controller: UserController) {
    query("findUserById") {
        resolver { id: UUID ->
            controller.findById(id).getOrThrow()
        }
    }

    mutation("update") {
        resolver { name: String, accountId: String, description: String, context: Context ->
            controller.update(context.getIdFromSession(), accountId, name, description).fold({ true }, { false })
        }

        accessRule(::requireAuthSession)
    }
}