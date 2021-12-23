package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.uramnoil.serverist.AuthSession
import com.uramnoil.serverist.exceptions.NoAuthorityException
import java.util.UUID

data class PageRequest(val limit: Int, val offset: Long)

fun requireAuthSession(context: Context): NoAuthorityException? =
    if (context.get<AuthSession>() == null) NoAuthorityException("権限がありません。") else null

fun Context.getIdFromSession(): UUID = get<AuthSession>()!!.id
