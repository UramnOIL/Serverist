package com.uramnoil.serverist.domain.serverist.models.server

import kotlinx.datetime.Instant
import kotlinx.datetime.isDistantFuture

data class CreatedAt(val value: Instant) {
    init {
        if (value.isDistantFuture) throw IllegalArgumentException("Only the present or past can be substituted.")
    }
}
