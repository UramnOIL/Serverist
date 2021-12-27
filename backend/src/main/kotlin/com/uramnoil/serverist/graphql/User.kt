package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.uramnoil.serverist.presenter.UserController
import kotlinx.serialization.Serializable
import java.util.UUID
import com.uramnoil.serverist.serverist.application.user.User as ApplicationUser

@Serializable
private data class User(
    val id: String,
    val accountId: String,
    val name: String,
    val description: String
)

private fun ApplicationUser.toGraphQlModel() = User(id.toString(), accountId, name, description)

fun SchemaBuilder.userSchema(controller: UserController) {
    query("findUserById") {
        resolver { id: String ->
            val result = controller.findById(UUID.fromString(id))
            result.getOrThrow()?.toGraphQlModel()
        }
    }

    mutation("updateUser") {
        resolver { name: String, accountId: String, description: String, context: Context ->
            val id = context.getIdFromSession()
            controller.update(id, accountId, name, description)
            controller.findById(id).getOrThrow()?.toString()
        }

        accessRule(::requireAuthSession)
    }
}
