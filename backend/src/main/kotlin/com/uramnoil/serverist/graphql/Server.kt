package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.presenter.ServerController
import java.util.*

fun SchemaBuilder.serverSchema(controller: ServerController) {
    suspend fun checkOwner(userId: UUID, serverId: UUID) {
        if (!controller.checkUserIsOwnerOfServer(userId, serverId).getOrThrow()) {
            throw IllegalArgumentException("権限がありません。")
        }
    }

    query("findServersByOwner") {
        resolver { ownerId: UUID, page: PageRequest?, sort: Sort?, orderBy: OrderBy? ->
            val result = controller.findServerByOwner(
                ownerId = ownerId,
                limit = page?.limit ?: 50,
                offset = page?.offset ?: 0,
                sort = sort ?: Sort.Asc,
                orderBy = orderBy ?: OrderBy.CreatedAt
            )
            result.getOrThrow()
        }
    }

    query("findServers") {
        resolver { page: PageRequest?, sort: Sort?, orderBy: OrderBy? ->
            controller.findAllServers(
                limit = page?.limit ?: 50,
                offset = page?.offset ?: 0,
                sort = sort ?: Sort.Asc,
                orderBy = orderBy ?: OrderBy.CreatedAt
            ).getOrThrow()
        }
    }

    query("findServer") {
        resolver { id: UUID ->
            val result = controller.findServerById(id)
            result.getOrThrow()
        }
    }

    mutation("createServer") {
        resolver { name: String, host: String?, port: Int?, description: String, context: Context ->
            val ownerId = context.getIdFromSession()
            controller.createServer(ownerId, name, host, port?.toUShort(), description).getOrThrow()
        }

        accessRule(::requireAuthSession)
    }

    mutation("updateServer") {
        resolver { id: UUID, name: String, host: String?, port: Int?, description: String, context: Context ->
            checkOwner(context.getIdFromSession(), id)
            controller.updateServer(id, name, host, port?.toUShort(), description).fold({ true }, { false })
        }

        accessRule(::requireAuthSession)
    }

    mutation("deleteServer") {
        resolver { id: UUID, context: Context ->
            checkOwner(context.getIdFromSession(), id)
            controller.deleteServer(id).fold({ true }, { false })
        }

        accessRule(::requireAuthSession)
    }
}