package com.uramnoil.serverist.serverist.application.usecases.user.queries

import kotlinx.datetime.Instant
import java.util.UUID

data class User(val id: UUID, val accountId: String, val name: String, val description: String, val createdAt: Instant, val iconId: UUID)