package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.uramnoil.serverist.presenter.UserController
import java.util.*

fun SchemaBuilder.userSchema(controller: UserController) {
    query("findUsersById") {
        resolver { id: UUID ->
            controller.findById(id)
        }
    }

    mutation("update") {
        resolver { name: String, accountId: String, description: String, context: Context ->
            controller.update(context.getIdFromSession(), accountId, name, description)
        }

        accessRule(::requireAuthSession)
    }

    mutation("delete") {
        resolver { context: Context ->
            controller.delete(context.getIdFromSession())
        }

        accessRule(::requireAuthSession)
    }
}