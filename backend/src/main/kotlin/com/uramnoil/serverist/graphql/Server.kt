package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.Sort
import com.uramnoil.serverist.presenter.ServerController
import com.uramnoil.serverist.server.application.queries.OrderBy

fun SchemaBuilder.serverSchema(controller: ServerController) {
    suspend fun checkOwner(userId: Uuid, serverId: Uuid) {
        if (!controller.checkUserIsOwnerOfServer(userId, serverId).getOrThrow()) {
            throw IllegalArgumentException("権限がありません。")
        }
    }

    query("serversById") {
        resolver { ownerId: Uuid, page: PageRequest, sort: Sort, orderBy: OrderBy ->
            controller.findServerByOwner(
                ownerId = ownerId,
                limit = page.limit,
                offset = page.offset,
                sort = sort,
                orderBy = orderBy
            )
        }
    }

    mutation("createServer") {
        resolver { name: String, address: String?, port: Int?, description: String, context: Context ->
            val ownerId = context.getIdFromSession()
            controller.createServer(ownerId, name, address, port, description)
        }

        accessRule(::checkSession)
    }

    mutation("updateServer") {
        resolver { id: Uuid, name: String, address: String?, port: Int?, description: String, context: Context ->
            checkOwner(context.getIdFromSession(), id)
            controller.updateServer(id, name, address, port, description)
        }

        accessRule(::checkSession)
    }

    mutation("deleteServer") {
        resolver { id: Uuid, context: Context ->
            checkOwner(context.getIdFromSession(), id)
            controller.deleteServer(id)
        }

        accessRule(::checkSession)
    }
}