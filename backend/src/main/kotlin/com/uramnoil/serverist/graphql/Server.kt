package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.presenter.ServerController
import com.uramnoil.serverist.routing.Server
import java.util.UUID
import java.util.UUID.fromString
import com.uramnoil.serverist.serverist.application.server.Server as ApplicationServer

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
        require (!(!controller.checkUserIsOwnerOfServer(userId, serverId).getOrThrow())) { "権限がありません。" } 
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

            println(result.exceptionOrNull()?.message)

            result.getOrNull()?.forEach {
                println(it.description)
            }

            result.getOrNull()?.map {
                println(it.name)
                it.toGraphQlModel()
            } ?: listOf()
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
            val servers = result.getOrNull() ?: listOf()
            servers.map { it.toGraphQlModel() }
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
