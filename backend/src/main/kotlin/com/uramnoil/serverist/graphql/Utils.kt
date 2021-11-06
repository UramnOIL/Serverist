package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.AuthSession


data class PageRequest(val limit: Int, val offset: Long)

fun requireAuthSession(context: Context): Exception? =
    if (context.get<AuthSession>() == null) Exception("権限がありません。") else null

fun Context.getIdFromSession(): Uuid = get<AuthSession>()!!.id