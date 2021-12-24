package com.uramnoil.serverist.serverist.infrastructure

import com.uramnoil.serverist.domain.serverist.models.server.Server
import com.uramnoil.serverist.domain.serverist.models.user.User
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.time.LocalDateTime
import java.time.ZoneOffset

fun LocalDateTime.toKotlinInstant() = toInstant(ZoneOffset.UTC).toKotlinInstant()

fun Instant.toJavaLocalDataTime() = LocalDateTime.ofInstant(toJavaInstant(), ZoneOffset.UTC)

fun Server.toApplication() = com.uramnoil.serverist.serverist.application.server.Server(
    id = id.value,
    createdAt = createdAt.value,
    ownerId = ownerId.value,
    name = name.value,
    host = host?.value,
    port = port?.value?.toInt(),
    description = description.value
)

fun User.toApplicationUser() = com.uramnoil.serverist.serverist.application.user.User(
    id.value,
    accountId.value,
    name.value,
    description.value
)
