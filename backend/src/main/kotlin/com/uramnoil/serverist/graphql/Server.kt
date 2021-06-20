package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.server.commands.DeleteServerCommand
import com.uramnoil.serverist.application.server.commands.UpdateServerCommand
import com.uramnoil.serverist.application.server.queries.FindServersByOwnerQuery
import com.uramnoil.serverist.application.server.queries.IsUserOwnerOfServer
import com.uramnoil.serverist.application.server.queries.OrderBy
import org.kodein.di.DI
import org.kodein.di.instance
import java.util.*

fun SchemaBuilder.serverSchema(di: DI) {
    suspend fun checkOwner(userId: UUID, serverId: UUID) {
        val query by di.instance<IsUserOwnerOfServer>()
        if (!query.execute(serverId, userId)) {
            throw IllegalArgumentException("権限がありません。")
        }
    }

    query("serversById") {
        resolver { id: UUID, page: PageRequest, sort: Sort, orderBy: OrderBy ->
            val query by di.instance<FindServersByOwnerQuery>()
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

            val command by di.instance<CreateServerCommand>()
            command.execute(ownerId, name, address, port, description)
        }

        accessRule(::checkSession)
    }

    mutation("updateServer") {
        resolver { id: UUID, name: String, address: String?, port: Int?, description: String, context: Context ->
            checkOwner(context.getIdFromSession(), id)

            val command by di.instance<UpdateServerCommand>()
            command.execute(id, name, address, port, description)

            id
        }

        accessRule(::checkSession)
    }

    mutation("deleteServer") {
        resolver { id: UUID, context: Context ->
            checkOwner(context.getIdFromSession(), id)

            val command by di.instance<DeleteServerCommand>()
            command.execute(id)
            id
        }

        accessRule(::checkSession)
    }
}