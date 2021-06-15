package com.uramnoil.serverist.graphql

import com.apurebase.kgraphql.Context
import com.uramnoil.serverist.AuthSession
import java.util.*


data class PageRequest(val limit: Int, val offset: Long)

fun Context.checkSession(): Exception? = if (get<AuthSession>() == null) Exception("権限がありません。") else null

fun Context.getIdFromSession(): UUID = get<AuthSession>()!!.id