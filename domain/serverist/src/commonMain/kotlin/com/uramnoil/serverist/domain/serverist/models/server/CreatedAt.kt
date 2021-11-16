package com.uramnoil.serverist.domain.serverist.models.server

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class CreatedAt(val value: Instant) {
    init {
        if (value > Clock.System.now()) throw IllegalArgumentException("Only the present or past can be substituted.")
    }
}
