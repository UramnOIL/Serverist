package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.presenter.ServerController
import kotlinx.serialization.Serializable
import java.util.UUID
import java.util.UUID.fromString
import com.uramnoil.serverist.serverist.application.server.Server as ApplicationServer

@Serializable
private data class Server(
    val id: String,
    val createdAt: Long,
    val ownerId: String,
    val name: String,
    val host: String?,
    val port: Int?,
    val description: String
)

private fun ApplicationServer.toGraphQlModel() = Server(
    id = id.toString(),
    createdAt = createdAt.toEpochMilliseconds(),
    ownerId = ownerId.toString(),
    name = name,
    host = host,
    port = port,
    description = description
)

fun SchemaBuilder.serverSchema(controller: ServerController) {
    suspend fun checkOwner(userId: UUID, serverId: UUID) {
        if (!controller.checkUserIsOwnerOfServer(userId, serverId).getOrThrow()) {
            throw IllegalArgumentException("権限がありません。")
        }
    }

    query("findServersByOwner") {
        resolver { ownerId: String, page: PageRequest?, sort: Sort?, orderBy: OrderBy? ->
            val result = controller.findServerByOwner(
                ownerId = fromString(ownerId),
                limit = page?.limit ?: 50,
                offset = page?.offset ?: 0,
                sort = sort ?: Sort.Asc,
                orderBy = orderBy ?: OrderBy.CreatedAt
            )
            result.getOrThrow().map { it.toGraphQlModel() }
        }
    }

    query("findServers") {
        resolver { page: PageRequest?, sort: Sort?, orderBy: OrderBy? ->
            val result = controller.findAllServers(
                limit = page?.limit ?: 50,
                offset = page?.offset ?: 0,
                sort = sort ?: Sort.Asc,
                orderBy = orderBy ?: OrderBy.CreatedAt
            )
            result.getOrThrow().map { it.toGraphQlModel() }
        }
    }

    query("findServer") {
        resolver { id: String ->
            val result = controller.findServerById(fromString(id))
            result.getOrThrow()?.toGraphQlModel()
        }
    }

    mutation("createServer") {
        resolver { name: String, host: String?, port: Int?, description: String, context: Context ->
            val ownerId = context.getIdFromSession()
            controller.createServer(ownerId, name, host, port?.toUShort(), description).getOrThrow().toString()
        }

        accessRule(::requireAuthSession)
    }

    mutation("updateServer") {
        resolver { id: String, name: String, host: String?, port: Int?, description: String, context: Context ->
            checkOwner(context.getIdFromSession(), fromString(id))
            controller.updateServer(fromString(id), name, host, port?.toUShort(), description).fold({ true }, { false })
        }

        accessRule(::requireAuthSession)
    }

    mutation("deleteServer") {
        resolver { id: String, context: Context ->
            checkOwner(context.getIdFromSession(), fromString(id))
            controller.deleteServer(fromString(id)).fold({ true }, { false })
        }

        accessRule(::requireAuthSession)
    }
}
