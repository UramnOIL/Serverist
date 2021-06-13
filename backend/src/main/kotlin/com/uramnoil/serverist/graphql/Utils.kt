package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.uramnoil.serverist.AuthSession


data class PageRequest(val limit: Int, val offset: Long)

fun checkSession(ctx: Context): Exception? = if (ctx.get<AuthSession>() == null) Exception("権限がありません。") else null