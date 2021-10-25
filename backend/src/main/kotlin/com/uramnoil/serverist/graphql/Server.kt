package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.commands.CreateServerCommandInputPort
import com.uramnoil.serverist.application.server.commands.DeleteServerCommandInputPort
import com.uramnoil.serverist.application.server.commands.UpdateServerCommandInputPort
import com.uramnoil.serverist.application.server.queries.FindServersByOwnerQueryInputPort
import com.uramnoil.serverist.application.server.queries.IsUserOwnerOfServer
import com.uramnoil.serverist.application.server.queries.OrderBy
import org.kodein.di.DI
import org.kodein.di.instance

fun SchemaBuilder.serverSchema(di: DI) {
    suspend fun checkOwner(userId: Uuid, serverId: Uuid) {
        val query by di.instance<IsUserOwnerOfServer>()
        if (query.execute(serverId, userId).isFailure) {
            throw IllegalArgumentException("権限がありません。")
        }
    }

    query("serversById") {
        resolver { id: Uuid, page: PageRequest, sort: Sort, orderBy: OrderBy ->
            val query by di.instance<FindServersByOwnerQueryInputPort>()
            query.execute(
                ownerId = id,
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

            val command by di.instance<CreateServerCommandInputPort>()
            command.execute(ownerId, name, address, port, description)
        }

        accessRule(::checkSession)
    }

    mutation("updateServer") {
        resolver { id: Uuid, name: String, address: String?, port: Int?, description: String, context: Context ->
            checkOwner(context.getIdFromSession(), id)

            val command by di.instance<UpdateServerCommandInputPort>()
            command.execute(id, name, address, port, description)

            id
        }

        accessRule(::checkSession)
    }

    mutation("deleteServer") {
        resolver { id: Uuid, context: Context ->
            checkOwner(context.getIdFromSession(), id)

            val command by di.instance<DeleteServerCommandInputPort>()
            command.execute(id)
            id
        }

        accessRule(::checkSession)
    }
}